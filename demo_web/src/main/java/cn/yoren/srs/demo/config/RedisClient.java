package cn.yoren.srs.demo.config;

import cn.yoren.srs.demo.common.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 *
 * 
 */
public class RedisClient {
	private Logger log = LoggerFactory.getLogger(RedisClient.class);
	
	private JedisPool jedisPool;

	private static String SERIAL_NUMBER = ".serial.number:";

	public void set(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value.toString());
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			jedis.close();
		}
	}

	/**
	 * 设置过期时间 
	 * @param key
	 * @param value
	 * @param exptime
	 * @throws Exception
	 */
	public void setWithExpireTime(String key, String value, int exptime) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value, "NX", "EX", 300);
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			jedis.close();
		}
	}

	public String get(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			if (jedis != null)
			jedis.close();
		}
		return null;
	}
	

	/**
	 * 存储任意类型为Json格式数据
	 * 
	 * @param key
	 *            存储键
	 * @param value
	 *            存储值
	 * @param timeOut
	 *            是否设置过期失效
	 * @param seconds
	 *            有效时间(单位：秒)
	 * @return
	 * @throws Exception
	 */
	public boolean set(String key, Object value, boolean timeOut,
			Integer seconds) throws Exception {

		if (StringUtil.isBlankOr(key, value)) {
			log.error("Redis set key or value 为空");
			return false;
		}

		Jedis jedis = null;
		try {
			delObj(key);
			jedis = jedisPool.getResource();
			boolean result = jedis.set(key, JSON.toJSONString(value))
					.toUpperCase().equalsIgnoreCase("OK");
			if (timeOut && seconds != null) {
				jedis.expire(key, seconds);
			}
			return result;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 存储Map
	 * 
	 * @param key
	 *            键
	 * @param map
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean set(String key, Map map) throws Exception {

		if (StringUtil.isBlankOr(key, map)) {
			log.error("Redis 存储Map key or value 为空");
			return false;
		}

		Jedis jedis = null;
		try {
			delObj(key);
			jedis = jedisPool.getResource();
			boolean result = jedis.hmset(key, map).equalsIgnoreCase("OK");
			return result;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 存储List
	 * 
	 * @param <W>
	 * @param key
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public <W extends List> boolean set(String key, W list)
			throws Exception {

		if (StringUtil.isBlankOr(key, list)) {
			log.error("Redis 存储List key or value 为空");
			return false;
		}

		Jedis jedis = null;
		try {
			delObj(key); // 先删除可能存在的KEY
			jedis = jedisPool.getResource();
			for (Object obj : list) {
				jedis.lpush(key, JSON.toJSONString(obj));
			}
			return true;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 存储Set
	 * 
	 * @param key
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public boolean set(String key, Set set) throws Exception {

		if (StringUtil.isBlankOr(key, set)) {
			log.error("Redis 存储Set key or value 为空");
			return false;
		}

		Jedis jedis = null;
		try {
			delObj(key);
			jedis = jedisPool.getResource();
			for (Object obj : set) {
				jedis.sadd(key, JSON.toJSONString(obj));
			}
			return true;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 获取Map值
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMap(String key) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return null;
			}
			jedis = jedisPool.getResource();
			Set<String> keySet = jedis.hkeys(key);
			if (keySet == null || keySet.isEmpty()) {
				return null;
			}
			Map<String, Object> data = new HashMap<String, Object>();
			String tempKey = null;
			List tempList = null;
			for (Iterator<String> iterator = keySet.iterator(); iterator
					.hasNext();) {
				tempKey = iterator.next();
				tempList = jedis.hmget(key, tempKey);
				data.put(tempKey, tempList != null ? tempList.get(0) : null);
			}
			return data;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 获取List类型数据
	 * 
	 * @param key
	 * @param cls
	 *            List集合中保存的对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getList(String key, Class cls) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return null;
			}
			if (cls == null) {
				cls = Object.class;
			}
			jedis = jedisPool.getResource();
			List<String> list = jedis.lrange(key, 0, -1);
			if (list == null || list.isEmpty()) {
				return null;
			}
			List<Object> data = new ArrayList<Object>();
			for (String str : list) {
				data.add(JSONObject.parseObject(str, cls));
			}
			return data;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 获取Set类型数据
	 * 
	 * @param key
	 * @param cls
	 *            Set集合中保存的对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Set getSet(String key, Class cls) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return null;
			}
			if (cls == null) {
				cls = Object.class;
			}
			jedis = jedisPool.getResource();
			Set<String> set = jedis.smembers(key);
			if (set == null || set.isEmpty()) {
				return null;
			}
			Set<Object> data = new HashSet<Object>();
			for (String str : set) {
				data.add(JSONObject.parseObject(str, cls));
			}
			return data;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 删除指定Key
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean delObj(String key) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return false;
			}
			jedis = jedisPool.getResource();
			boolean exists = jedis.exists(key);
			if (!exists)
				return true;
			return jedis.del(key) > 0 ? true : false;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 删除Hash指定KEY
	 * 
	 * @param mainKey
	 *            Hash主键
	 * @param key
	 *            Hash子键
	 * @return
	 * @throws Exception
	 */
	public boolean hdelObj(String mainKey, String key) throws Exception {

		if (StringUtil.isBlankOr(mainKey, key))
			return false;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			boolean exists = hExists(mainKey, key);
			if (!exists)
				return true;
			return jedis.hdel(mainKey, key) > 0 ? true : false;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 判断Key是否存在
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String key) throws Exception {

		if (StringUtil.isBlank(key))
			return false;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 判断Hash中Key是否存在
	 * 
	 * @param mainKey
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean hExists(String mainKey, String key) throws Exception {

		if (StringUtil.isBlankOr(mainKey, key))
			return false;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hexists(mainKey, key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;

	}

	/**
	 * 设置有效时间
	 * 
	 * @param key
	 * @param seconds
	 *            有效时间(单位：秒)
	 * @return
	 * @throws Exception
	 */
	public boolean expire(String key, int seconds) throws Exception {

		if (StringUtil.isBlank(key))
			return false;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.expire(key, seconds) > 0 ? true : false;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}

	/**
	 * 获取还剩多长时间过期
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public long getTTL(String key) throws Exception {

		if (StringUtil.isBlank(key))
			return 0L;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(key)) {
				return jedis.ttl(key);
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return -2;
	}

	/**
	 * 获取Value类型
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getValueType(String key) throws Exception {

		if (StringUtil.isBlank(key))
			return "";

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.type(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 获取当前Redis中所有KEY
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Set<String> getAllKeys(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> set = jedis.keys(key);
			return set;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 一旦拿到的jedis实例使用完毕，必须要返还给池中
	 * 
	 * @param pool
	 * @param jedis
	 */
	public void returnResource(JedisPool pool, Jedis jedis) {
		try {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (pool != null && jedis != null) {
				pool.returnResource(jedis);
			}
		} catch (Exception e) {
			log.error("returnResource 异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 释放redis对象
	 * 
	 * @param pool
	 * @param jedis
	 */
	public void returnBrokenResource(JedisPool pool, Jedis jedis) {
		try {
			if (pool != null && jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Redis_Hash存储
	 * 
	 * @param mainKey
	 *            主键
	 * @param key
	 *            子键
	 * @param obj
	 *            存储值
	 * @return
	 * @throws Exception
	 */

	public boolean hset(String mainKey, String key, Object obj)
			throws Exception {
		return hset(mainKey, key, obj, false, null);
	}

	/**
	 * Redis_Hash存储，设计为私有方法，目前hset设置失效时间无用
	 * 
	 * @param mainKey
	 * @param key
	 * @param obj
	 * @param isTimeOut
	 * @param seconds
	 * @return
	 * @throws Exception
	 */
	private boolean hset(String mainKey, String key, Object obj,
			boolean isTimeOut, Integer seconds) throws Exception {

		if (StringUtil.isBlankOr(mainKey, key, obj)) {
			return false;
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(mainKey, key, JSON.toJSONString(obj));
			if (isTimeOut == true && null != seconds) {
				jedis.expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}
	
	/**
	 * Redis_Hash存储，设计为私有方法，目前hset设置失效时间无用
	 * 
	 * @param mainKey
	 * @param key
	 * @param obj
	 * @param isTimeOut
	 * @param seconds
	 * @return
	 * @throws Exception
	 */
	public long hsetnx(String key, String field, String value,
			boolean isTimeOut, Integer seconds) throws Exception {
		
		if (StringUtil.isBlankOr(key, field, value)) {
			return 0L;
		}
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long ret = jedis.hsetnx(key, field, value);
			if (isTimeOut == true && null != seconds) {
				jedis.expire(key, seconds);
			}
			return ret;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return 0L;
	}

	/**
	 * 获取任意对象类型数据，将Json格式字符串转换成对象
	 * 
	 * @param <W>
	 * @param key
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <W extends Object> W getObject(String key, Class t)
			throws Exception {
		String data = getObject(key);
		if(null == data || data.equals("") || null == t)
			return null;
		return (W) JSON.parseObject(data, t);
	}
	
	/**
	 * 获取Hash值
	 * @param <T>
	 * @param mainKey
	 * @param key
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public <T> T getObject(String mainKey, String key, Class<T> t)throws Exception {
		String data = getObject(mainKey, key);
		if(null == data || data.equals("") || null == t)
			return null;
		return (T) JSON.parseObject(data, t);
	}
	
	public String getObject(String mainKey, String key)
			throws Exception {

		if (key == null || key.equals("")) {
			return null;
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String data = jedis.hget(mainKey, key);
			if (data == null || data.equals("")) {
				return null;
			}
			return data;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}
	
	public String getObject(String key)throws Exception {

		if (key == null || key.equals("")) {
			return null;
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String data = jedis.get(key);
			if (data == null || data.equals("")) {
				return null;
			}
			return data;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	/**
	 * 查询Hash Key下的所有hash值
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> hgetAll(String key) throws Exception {
		
		if(StringUtil.isBlank(key)){
			return null;
		}
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}
	
	/**
	 * 查询Hash Key下的field值
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String hget(String key, String field) throws Exception {
		
		if(StringUtil.isBlank(key)){
			return null;
		}
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hget(key, field);
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}
	

	/**
	 * Redis_set存储
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean sadd(String key, String member, 
			boolean isTimeOut, Integer seconds) throws Exception {

		if (StringUtil.isBlankOr(key, member)) {
			return false;
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.sadd(key, member);
			if (isTimeOut == true && null != seconds) {
				jedis.expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return false;
	}
	
	/**
	 * 判断 member 是否是key集合的元素
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean sismember(String key, String member) throws Exception {
		
		if(StringUtil.isBlank(key)){
			return null;
		}
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.sismember(key, member);
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return null;
	}

	public Long incr(String key) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return 0L;
			}
			jedis = jedisPool.getResource();
			return jedis.incr(key);
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return 0L;
	}
	
	public Long decr(String key) throws Exception {
		Jedis jedis = null;
		try {
			if (key == null || key.equals("")) {
				return 0L;
			}
			jedis = jedisPool.getResource();
			return jedis.decr(key);
		} catch (Exception e) {
			returnBrokenResource(jedisPool, jedis);
			log.error(e.getMessage(), e);
		} finally {
			returnResource(jedisPool, jedis);
		}
		return 0L;
	}

	/**
	 * Remove and get the first element in a list
	 *
	 * @param key
	 * @return
	 * @since qlchat 1.0
	 */
	public String lpop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.lpop(key);
		} finally {
			returnResource(jedisPool, jedis);
		}
	}

	/**
	 * Remove and get the first element in a list
	 *
	 * @param key
	 * @return
	 * @since qlchat 1.0
	 */
	public Long lpush(String key,String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.lpush(key,value);
		} finally {
			returnResource(jedisPool, jedis);
		}
	}

	public String makeSeqNo(String orgId,String business,String tranTime,int length) throws Exception {
		String today = tranTime.substring(0,8);
		/** 构造redis的key */
		String key = business + SERIAL_NUMBER + today;
		/** 自增 */
		long sequence = this.incr(key);
		String seq = this.getSequence(sequence,length);
		StringBuilder builder = new StringBuilder(today);
		builder.append(orgId).append(seq);
		return builder.toString();
	}


	/**
	 * 得到6位的序列号,长度不足6位,前面补0
	 *
	 * @param seq
	 * @return
	 */
	private String getSequence(long seq,int length) {
		String str = String.valueOf(seq);
		int len = str.length();
		if (len >= length) {// 取决于业务规模
			return str;
		}
		int rest = length - len;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rest; i++) {
			sb.append('0');
		}
		sb.append(str);
		return sb.toString();
	}
	public void main(String[] args) {
		try {
//			String ip = "redis.test.lianzhongjr.net";
//			Integer port = 6379;
//			String password = "79mFpdn0gR";
//			init(ip, port, password);
			
//			Map<String,Object> s = getObject("config_center", "XWYH_IMG-0", Map.class);
//			System.out.println(s);
//			List<Map<String,Object>> list = getObject("config_center", "key-list", List.class);
//			System.out.println(list.get(0).get("pwd"));
			
			//List<UserDto> userList = getObject("config_center", "key-list", List.class);
			//System.out.println(userList.get(0).getUserName());
			set("fin-web_test", "0", false, 10);
//			for(int i=0; i<1000; i++) {
//				
//				long test = incr("fin_web_test1");
//				System.out.println(i + "次:" + test);
//			}
			String v = getObject("fin-web_test");
			System.out.println(v);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}

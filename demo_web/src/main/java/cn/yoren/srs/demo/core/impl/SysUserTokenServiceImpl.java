package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysUserTokenBean;
import cn.yoren.srs.demo.core.service.SysUserTokenService;
import cn.yoren.srs.demo.domain.dao.SysUserTokenMapper;
import cn.yoren.srs.demo.utils.R;
import cn.yoren.srs.demo.utils.TokenGenerator;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Resource
	SysUserTokenMapper sysUserTokenMapper;
	@Override
	public Page queryPage(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<SysUserTokenBean> queryList(Map<String, Object> params) {
		return null;
	}

	@Override
	public HashMap<String, Object> createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserTokenBean tokenEntity = sysUserTokenMapper.selectByPrimaryKey(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenBean();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			sysUserTokenMapper.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", EXPIRE);

		return map;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//修改token
		SysUserTokenBean tokenEntity = new SysUserTokenBean();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
	}

	/**
	 * 根据token获取用户id
	 *
	 * @param token
	 */
	@Override
	public SysUserTokenBean queryByToken(String token) {
		return sysUserTokenMapper.queryByToken(token);
	}
}

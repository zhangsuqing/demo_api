package cn.yoren.srs.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * 
 */
@ConfigurationProperties(prefix = JedisProperties.JEDIS_PREFIX)
public class JedisProperties {
	public static final String JEDIS_PREFIX = "redis.server";

	private String host;

	private int port;

	private String password;

	private int maxTotal;

	private int maxIdle;

	private int maxWaitMillis;

	private int timeOut;

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
}

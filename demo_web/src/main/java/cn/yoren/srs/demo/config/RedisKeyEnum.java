package cn.yoren.srs.demo.config;

import static com.google.common.base.Objects.equal;

import com.google.common.base.MoreObjects;

/**
 * Created by miaojun on 2019/4/16.
 */
public enum RedisKeyEnum {

  REDIS_IP_EXPIRE_SECOND("redis:ip:expire:second", "IP重复提交限制过期秒数"),
  REDIS_RPC_INTERFACE_VISIT_IP("redis:rpc:interface:visit:ip", "IP重复提交访问的IP的KEY"),
  REDIS_SHORT_MESSAGE_MERCHANT("redis:short:message:merchant","短信使用商户");
  ;

  private final String code;
  private final String message;


  private RedisKeyEnum(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static RedisKeyEnum fromValue(String code) {
    for (RedisKeyEnum obj : values()) {
      if (equal(code, obj.getCode())) {
        return obj;
      }
    }
    return null;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("code", this.code)
        .add("message", this.message).toString();
  }
}

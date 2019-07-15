package cn.yoren.srs.demo.core.service;

/**
 * 系统验证码
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
public interface SysCaptchaService {

    /**
     * 获取图片验证码
     */
    String getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}


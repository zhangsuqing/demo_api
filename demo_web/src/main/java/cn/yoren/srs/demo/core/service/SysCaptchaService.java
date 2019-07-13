package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysCaptchaBean;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.List;

/**
 * 系统验证码
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
public interface SysCaptchaService {

    Page queryPage(Map<String, Object> params);

    List<SysCaptchaBean> queryList(Map<String, Object> params);

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


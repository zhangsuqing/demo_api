package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysUserTokenBean;
import cn.yoren.srs.demo.utils.R;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 系统用户Token
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
public interface SysUserTokenService{
    /**
     * 生成token
     * @param userId  用户ID
     */
    HashMap<String, Object> createToken(long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);

    /**
     * 根据token获取用户id
     * @param token
     */
    SysUserTokenBean queryByToken(String token);
    /**
     * 更新token过期时间
     */
    int updateTokenExpire(Long userId);
}


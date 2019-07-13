package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.common.entity.SysUserTokenBean;

import java.util.Set;

/**
 * shiro相关接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenBean queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserBean queryUser(Long userId);
}

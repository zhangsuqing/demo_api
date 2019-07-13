package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysRoleBean;
import cn.yoren.srs.demo.common.entity.SysUserBean;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;

import java.util.Map;
import java.util.List;

/**
 * 系统用户
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
public interface SysUserService{
    PageInfo<SysUserBean> queryPage(Map<String, Object> params);

    SysUserBean selectByPrimaryKey(Long userId);


    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserBean queryByUserName(String username);

    /**
     * 保存用户
     */
    void save(SysUserBean user);

    /**
     * 修改用户
     */
    void update(SysUserBean user);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);
}


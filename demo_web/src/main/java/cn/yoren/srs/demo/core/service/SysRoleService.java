package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysRoleBean;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.Map;
import java.util.List;

/**
 * 角色
 *
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
public interface SysRoleService{
    /**
     * 角色分页
     * @param params
     * @return
     */
    PageInfo<SysRoleBean> queryPage(Map<String, Object> params);
    /**
     * 角色列表
     * @return
     */
    List<SysRoleBean> queryList();
    /**
     * 新增角色
     * @param role
     */
    void save(SysRoleBean role);
    /**
     * 修改角色
     * @param role
     */
    void update(SysRoleBean role);
    /**
     * 删除角色
     * @param roleId
     */
    void deleteBatch(Long roleId);
    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    /**
     * 角色详情
     * @param id
     * @return
     */
    SysRoleBean selectByPrimaryKey(Long id);
}


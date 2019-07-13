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

    PageInfo<SysRoleBean> queryPage(Map<String, Object> params);

    List<SysRoleBean> queryList();

    void save(SysRoleBean role);

    void update(SysRoleBean role);

    void deleteBatch(Long roleId);


    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
    SysRoleBean selectByPrimaryKey(Long id);
}


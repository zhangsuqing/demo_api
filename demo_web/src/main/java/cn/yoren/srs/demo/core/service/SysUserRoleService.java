package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysUserRoleBean;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;
import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
public interface SysUserRoleService{

    Page queryPage(Map<String, Object> params);

    List<SysUserRoleBean> queryList(Map<String, Object> params);

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long roleId,Long userId);
}


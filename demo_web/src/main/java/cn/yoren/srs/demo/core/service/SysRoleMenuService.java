package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysRoleMenuBean;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
public interface SysRoleMenuService{

    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<SysMenuBean> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
    /**
     *根据菜单id删除角色菜单关联
     * @param menuId 菜单id
     * @return
     */
    int deleteRoleMenuByMenuId(@Param("menuId") Long menuId);
}


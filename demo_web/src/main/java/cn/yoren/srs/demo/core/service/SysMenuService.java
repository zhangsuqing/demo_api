package cn.yoren.srs.demo.core.service;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;
import java.util.List;

/**
 * 菜单管理
 *
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
public interface SysMenuService{
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuBean> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuBean> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuBean> getUserMenuList(Long userId);

    /**
     * 删除菜单
     */
    void delete(Long menuId);

    /**
     * 获取所有菜单那
     * @return
     */
    List<SysMenuBean> selectMenuList();

    /**
     * 获取菜单详情
     * @param menuId
     * @return
     */
    SysMenuBean selectByPrimaryKey(Long menuId);

    /**
     * 新增菜单
     * @param sysMenuBean
     * @return
     */
    int insertMenu(SysMenuBean sysMenuBean);

    /**
     * 修改菜单
     * @param sysMenuBean
     * @return
     */
    int updateMenu(SysMenuBean sysMenuBean);
}


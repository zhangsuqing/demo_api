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

    Page queryPage(Map<String, Object> params);

    List<SysMenuBean> queryList(Map<String, Object> params);
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuBean> queryListParentId(Long parentId, List<Long> menuIdList);

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
     * 删除
     */
    void delete(Long menuId);
    List<SysMenuBean> selectMenuList();
    SysMenuBean selectByPrimaryKey(Long menuId);
    int inserMenu(SysMenuBean sysMenuBean);
    int updateMenu(SysMenuBean sysMenuBean);
}


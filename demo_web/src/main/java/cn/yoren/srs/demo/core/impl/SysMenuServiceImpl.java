package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.core.service.SysMenuService;
import cn.yoren.srs.demo.core.service.SysRoleMenuService;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.domain.dao.SysMenuMapper;
import cn.yoren.srs.demo.utils.Constant;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    SysMenuMapper sysMenuMapper;
    @Resource
    SysUserService sysUserService;
    @Resource
    SysRoleMenuService sysRoleMenuService;

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    @Override
    public List<SysMenuBean> queryListParentId(Long parentId) {
        return sysMenuMapper.queryListParentId(parentId);
    }
    /**
     * 获取不包含按钮的菜单列表
     */
    @Override
    public List<SysMenuBean> queryNotButtonList() {
        return sysMenuMapper.queryNotButtonList();
    }

    /**
     * 获取当前用户拥有的所有菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuBean> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 删除菜单
     * @param menuId
     */
    @Override
    @Transactional
    public void delete(Long menuId){
        //删除菜单
        sysMenuMapper.deleteByPrimaryKey(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.deleteRoleMenuByMenuId(menuId);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuBean> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenuBean> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuBean> getMenuTreeList(List<SysMenuBean> menuList, List<Long> menuIdList){
        List<SysMenuBean> subMenuList = new ArrayList<SysMenuBean>();

        for(SysMenuBean entity : menuList){
            //目录
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    private List<SysMenuBean> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuBean> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenuBean> userMenuList = new ArrayList<>();
        for(SysMenuBean menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<SysMenuBean> selectMenuList() {
        return sysMenuMapper.selectMenuList();
    }

    @Override
    public SysMenuBean selectByPrimaryKey(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int insertMenu(SysMenuBean sysMenuBean) {
        return sysMenuMapper.insert(sysMenuBean);
    }

    @Override
    public int updateMenu(SysMenuBean sysMenuBean) {
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenuBean);
    }

}

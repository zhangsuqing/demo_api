package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysRoleBean;
import cn.yoren.srs.demo.core.service.SysRoleMenuService;
import cn.yoren.srs.demo.core.service.SysRoleService;
import cn.yoren.srs.demo.core.service.SysUserRoleService;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.domain.dao.SysRoleMapper;
import cn.yoren.srs.demo.exception.RRException;
import cn.yoren.srs.demo.utils.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 角色分页
     * @param params
     * @return
     */
    @Override
    public PageInfo<SysRoleBean> queryPage(Map<String, Object> params) {
        Integer currentPage = Integer.parseInt(null==params.get("currentPage")?"1":(String) params.get("currentPage"));
        Integer pageSize = Integer.parseInt(null==params.get("pageSize")?"10":(String) params.get("pageSize"));
        PageHelper.startPage(currentPage,pageSize);
        List<SysRoleBean> addressBeanList = sysRoleMapper.getRoleList();
        PageInfo<SysRoleBean> pageInfo = new PageInfo<>(addressBeanList);
        return pageInfo;
    }

    /**
     * 角色列表
     * @return
     */
    @Override
    public List<SysRoleBean> queryList() {
        return sysRoleMapper.getRoleList();
    }

    /**
     * 新增角色
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleBean role) {
        role.setCreateTime(new Date());
        sysRoleMapper.insert(role);
        //检查权限是否越权
//        checkPrems(role);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleBean role) {
        sysRoleMapper.updateByPrimaryKeySelective(role);

        //检查权限是否越权
//        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long roleId) {
        //删除角色
        sysRoleMapper.deleteByPrimaryKey(roleId);
        Long[] roleIds = new Long[1];
        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleId,null);
    }

    /**
     * 查询当前用户创建的角色
     * @param createUserId
     * @return
     */
    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return sysRoleMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
//    private void checkPrems(SysRoleBean role){
//        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
//        if(role.getCreateUserId() == Constant.SUPER_ADMIN){
//            return ;
//        }
//
//        //查询用户所拥有的菜单列表
//        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
//
//        //判断是否越权
//        if(!menuIdList.containsAll(role.getMenuIdList())){
//            throw new RRException("新增角色的权限，已超出你的权限范围");
//        }
//    }

    /**
     * 查看角色详情
     * @param id
     * @return
     */
    @Override
    public SysRoleBean selectByPrimaryKey(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }
}

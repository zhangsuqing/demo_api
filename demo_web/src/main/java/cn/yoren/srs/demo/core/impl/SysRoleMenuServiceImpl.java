package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysRoleMenuBean;
import cn.yoren.srs.demo.core.service.SysRoleMenuService;
import cn.yoren.srs.demo.domain.dao.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<SysRoleMenuBean> list = new ArrayList<>(menuIdList.size());
        for(Long menuId : menuIdList){
            SysRoleMenuBean sysRoleMenuEntity = new SysRoleMenuBean();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);
            sysRoleMenuMapper.insert(sysRoleMenuEntity);
//            list.add(sysRoleMenuEntity);
        }
//        sysRoleMenuMapper.insertBatch(list);
    }

    @Override
    public List<SysMenuBean> queryMenuIdList(Long roleId) {
        return sysRoleMenuMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return sysRoleMenuMapper.deleteBatch(roleIds);
    }

    /**
     * 根据菜单id删除角色菜单关联
     *
     * @param menuId 菜单id
     * @return
     */
    @Override
    public int deleteRoleMenuByMenuId(Long menuId) {
        return sysRoleMenuMapper.deleteRoleMenuByMenuId(menuId);
    }
}

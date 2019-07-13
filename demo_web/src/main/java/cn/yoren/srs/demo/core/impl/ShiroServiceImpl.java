package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.common.entity.SysUserTokenBean;
import cn.yoren.srs.demo.core.service.ShiroService;
import cn.yoren.srs.demo.domain.dao.SysMenuMapper;
import cn.yoren.srs.demo.domain.dao.SysUserMapper;
import cn.yoren.srs.demo.domain.dao.SysUserTokenMapper;
import cn.yoren.srs.demo.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuBean> menuList = sysMenuMapper.selectMenuList();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuBean menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenBean queryByToken(String token) {
        return sysUserTokenMapper.queryByToken(token);
    }

    @Override
    public SysUserBean queryUser(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }
}

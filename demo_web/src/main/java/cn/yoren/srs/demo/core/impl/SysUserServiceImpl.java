package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.core.service.SysRoleService;
import cn.yoren.srs.demo.core.service.SysUserRoleService;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.domain.dao.SysUserMapper;
import cn.yoren.srs.demo.exception.RRException;
import cn.yoren.srs.demo.utils.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.List;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import javax.annotation.Resource;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUserBean> queryPage(Map<String, Object> params) {
        Integer currentPage = Integer.parseInt(null==params.get("currentPage")?"1":(String) params.get("currentPage"));
        Integer pageSize = Integer.parseInt(null==params.get("pageSize")?"10":(String) params.get("pageSize"));
        PageHelper.startPage(currentPage,pageSize);
        List<SysUserBean> userList = sysUserMapper.selectUserList();
        PageInfo<SysUserBean> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public SysUserBean selectByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserMapper.queryAllPerms(userId);
    }

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     */
    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserMapper.queryAllMenuId(userId);
    }

    /**
     * 根据用户名，查询系统用户
     *
     * @param username
     */
    @Override
    public SysUserBean queryByUserName(String username) {
        return sysUserMapper.queryByUserName(username);
    }

    /**
     * 保存用户
     *
     * @param user
     */
    @Override
    public void save(SysUserBean user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        sysUserMapper.insert(user);

        //检查角色是否越权
//        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Override
    public void update(SysUserBean user) {
        if(org.apache.commons.lang.StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserMapper.updateByPrimaryKeySelective(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    /**
     * 删除用户
     *
     * @param userIds
     */
    @Override
    public void deleteBatch(Long[] userIds) {
        for(Long id :userIds){
            sysUserMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserBean userEntity = new SysUserBean();
        userEntity.setPassword(newPassword);
        return sysUserMapper.updateByPrimaryKeySelective(userEntity)>0?true:false;
    }
    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserBean user){
        if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}

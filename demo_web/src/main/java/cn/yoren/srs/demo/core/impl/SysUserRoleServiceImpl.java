package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysUserRoleBean;
import cn.yoren.srs.demo.core.service.SysUserRoleService;
import cn.yoren.srs.demo.domain.dao.SysUserRoleMapper;
import cn.yoren.srs.demo.utils.MapUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Resource
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public Page queryPage(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<SysUserRoleBean> queryList(Map<String, Object> params) {
		return null;
	}

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		sysUserRoleMapper.deleteBatch(null,userId);

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		List<SysUserRoleBean> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRoleBean sysUserRoleEntity = new SysUserRoleBean();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			sysUserRoleMapper.insert(sysUserRoleEntity);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long roleId,Long userId){
		return sysUserRoleMapper.deleteBatch(roleId,userId);
	}

}

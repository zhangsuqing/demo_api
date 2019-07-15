package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysRoleBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色
 * 
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
@Mapper
public interface SysRoleMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleBean record);

    int insertSelective(SysRoleBean record);

    SysRoleBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleBean record);

    int updateByPrimaryKey(SysRoleBean record);
    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    /**
     * 获取所有角色
     * @return
     */
    List<SysRoleBean> getRoleList();
}

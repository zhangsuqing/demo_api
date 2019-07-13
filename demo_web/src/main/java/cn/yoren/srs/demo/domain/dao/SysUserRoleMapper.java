package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysUserRoleBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
@Mapper
public interface SysUserRoleMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRoleBean record);

    int insertSelective(SysUserRoleBean record);

    SysUserRoleBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRoleBean record);

    int updateByPrimaryKey(SysUserRoleBean record);
    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);


    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(@Param("roleId")Long roleId,@Param("userId")Long userId);
}

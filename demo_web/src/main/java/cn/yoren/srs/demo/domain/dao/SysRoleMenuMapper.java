package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysRoleMenuBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
@Mapper
public interface SysRoleMenuMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenuBean record);

    int insertSelective(SysRoleMenuBean record);

    SysRoleMenuBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenuBean record);

    int updateByPrimaryKey(SysRoleMenuBean record);
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<SysMenuBean> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     *根据菜单id删除角色菜单关联
     * @param menuId 菜单id
     * @return
     */
    int deleteRoleMenuByMenuId(@Param("menuId") Long menuId);
}

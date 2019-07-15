package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 * 
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
@Mapper
public interface SysUserMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysUserBean record);

    int insertSelective(SysUserBean record);

    SysUserBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserBean record);

    int updateByPrimaryKey(SysUserBean record);
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserBean queryByUserName(String username);

    /**
     * 获取所有用户信息
     * @return
     */
    List<SysUserBean> selectUserList();
}

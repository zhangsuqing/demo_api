package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysUserBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
@Mapper
public interface SysMenuMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysMenuBean record);

    int insertSelective(SysMenuBean record);

    SysMenuBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenuBean record);

    int updateByPrimaryKey(SysMenuBean record);
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuBean> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuBean> queryNotButtonList();

    List<SysMenuBean> selectMenuList();
}

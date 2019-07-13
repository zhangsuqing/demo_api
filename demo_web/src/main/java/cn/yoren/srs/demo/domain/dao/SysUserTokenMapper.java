package cn.yoren.srs.demo.domain.dao;

import cn.yoren.srs.demo.common.entity.SysUserTokenBean;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 * 
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
@Mapper
public interface SysUserTokenMapper{
    int deleteByPrimaryKey(Long id);

    int insert(SysUserTokenBean record);

    int insertSelective(SysUserTokenBean record);

    SysUserTokenBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserTokenBean record);

    int updateByPrimaryKey(SysUserTokenBean record);

    SysUserTokenBean queryByToken(String token);
}

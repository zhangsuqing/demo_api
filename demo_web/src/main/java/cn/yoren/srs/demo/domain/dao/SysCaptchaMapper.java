package cn.yoren.srs.demo.domain.dao;


import cn.yoren.srs.demo.common.entity.SysCaptchaBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统验证码
 * 
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
@Mapper
public interface SysCaptchaMapper{
	int deleteByPrimaryKey(String id);

	int insert(SysCaptchaBean record);

	int insertSelective(SysCaptchaBean record);

	SysCaptchaBean selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysCaptchaBean record);

	int updateByPrimaryKey(SysCaptchaBean record);
}

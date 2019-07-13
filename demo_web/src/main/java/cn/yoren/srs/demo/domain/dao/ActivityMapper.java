package cn.yoren.srs.demo.domain.dao;


import cn.yoren.srs.demo.common.entity.ActivityBean;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ActivityMapper{
    /**
     * 根据活动id获取活动信息
     * @param activityId 活动主键
     * @return
     */
    ActivityBean getActivityById(@Param("activityId") Long activityId);
    /**
     * 根据活动code获取活动信息
     * @param activityCode 活动code
     * @return
     */
    ActivityBean getActivityByActivityCode(@Param("activityCode") String activityCode);

    /**
     * 活动列表
     * @return
     */
    List<ActivityBean> getActivityList(@Param("activityName") String activityName);

    /**
     * 新增活动
     * @param activityBean
     * @return
     */
    Long addActivity(ActivityBean activityBean);

    /**
     * 修改活动
     * @param activityBean
     * @return
     */
    Long updateActivity(ActivityBean activityBean);
}

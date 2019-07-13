package cn.yoren.srs.demo.core;

import cn.yoren.srs.demo.common.entity.ActivityBean;

/**
 * Created by miaojun on 2019/7/8.
 */
public interface DemoService {
    /**
     * 根据活动code获取活动信息
     * @param activityCode 活动code
     * @return
     */
    ActivityBean getActivityByActivityCode(String activityCode);

}

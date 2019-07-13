package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.ActivityBean;
import cn.yoren.srs.demo.core.DemoService;
import cn.yoren.srs.demo.domain.dao.ActivityMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by miaojun on 2019/7/8.
 */
@Service
public class DemoServiceImpl implements DemoService {

  @Resource
  ActivityMapper activityMapper;

    /**
     * 根据活动code获取活动信息
     *
     * @param activityCode 活动code
     * @return
     */
    @Override
    public ActivityBean getActivityByActivityCode(String activityCode) {
      return activityMapper.getActivityByActivityCode(activityCode);
  }
}

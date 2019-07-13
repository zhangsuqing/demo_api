package cn.yoren.srs.demo.web.controller;

import cn.yoren.srs.demo.common.dto.ApiResponse;
import cn.yoren.srs.demo.common.entity.ActivityBean;
import cn.yoren.srs.demo.core.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/")
public class DemoController {
    @Autowired
    private DemoService demoService;
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ApiResponse updateActivity() {
        ActivityBean t =demoService.getActivityByActivityCode("test");
        return new ApiResponse("success","交易成功",t);
    }
}

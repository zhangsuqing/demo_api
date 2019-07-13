package cn.yoren.srs.demo.web.controller;


import javax.annotation.Resource;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.core.service.SysUserRoleService;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.utils.JsonData;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author zsq
 * @date 2019-07-09 16:29:43
 */
@RequestMapping("/backend/user")
@RestController
public class SysUserController extends AbstractController{

    @Resource
    private SysUserService sysUserService;
    @Resource
    SysUserRoleService sysUserRoleService;
    /**
     * 分页获取数据
     * @param params
     * @return JsonData
     */
    @RequestMapping(value="/fetchPage",method = RequestMethod.GET)
    public JsonData page(@RequestParam Map<String, Object> params){
        PageInfo<SysUserBean> page= sysUserService.queryPage(params);
        return JsonData.ok().setData(page);
    }
    /**
    * 通过id获取信息
    * @param userId
    * @return JsonData
    */
    @RequestMapping(value="/fetch/{userId}",method = RequestMethod.GET)
    public JsonData info(@PathVariable("userId") Long userId){
		SysUserBean sysUser = sysUserService.selectByPrimaryKey(userId);
        List<Long> roleList = sysUserRoleService.queryRoleIdList(userId);

        HashMap<String,Object> map = new HashMap<>();
        map.put("user",sysUser);
        map.put("roleList",roleList);
        return JsonData.ok().setData(map);
    }

    /**
    * 保存
    * @param sysUserBean
    * @return JsonData
    */
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public JsonData save(@RequestBody SysUserBean sysUserBean){
		sysUserService.save(sysUserBean);
        return JsonData.ok();
    }

    /**
    * 修改
    * @param sysUserBean
    * @return JsonData
    */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public JsonData update(@RequestBody SysUserBean sysUserBean){
		sysUserService.update(sysUserBean);
        return JsonData.ok();
    }

    /**
    * 删除
    * @param userIds
    * @return JsonData
    */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public JsonData delete(@RequestBody Long[] userIds){
		sysUserService.deleteBatch(userIds);
        return JsonData.ok();
    }

}

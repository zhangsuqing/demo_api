package cn.yoren.srs.demo.web.controller;

import java.util.*;

import javax.annotation.Resource;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysRoleBean;
import cn.yoren.srs.demo.core.service.SysRoleMenuService;
import cn.yoren.srs.demo.core.service.SysRoleService;
import cn.yoren.srs.demo.utils.JsonData;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;



/**
 * 角色
 *
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
@RequestMapping("/backend/role")
@RestController
public class SysRoleController extends AbstractController{

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    SysRoleMenuService sysRoleMenuService;

    /**
    * 分页获取数据
    * @param params
    * @return JsonData
    */
    @RequestMapping(value="/fetchPage",method = RequestMethod.GET)
    public JsonData page(@RequestParam Map<String, Object> params){
        PageInfo<SysRoleBean> page= sysRoleService.queryPage(params);
        return JsonData.ok().setData(page);
    }

    /**
    * 列表获取数据
    * @param params
    * @return JsonData
    */
    @RequestMapping(value="/fetchList",method = RequestMethod.GET)
    public JsonData list(@RequestParam Map<String, Object> params){

        List<SysRoleBean> list = sysRoleService.queryList();
        return JsonData.ok().setData(list);

    }

    /**
    * 通过id获取信息
    * @param roleId
    * @return JsonData
    */
    @RequestMapping(value="/fetch/{roleId}",method = RequestMethod.GET)
    public JsonData info(@PathVariable("roleId") Long roleId){
		SysRoleBean sysRole = sysRoleService.selectByPrimaryKey(roleId);

        List<SysMenuBean> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);

        HashMap<String,Object> map = new HashMap<>();
        map.put("role",sysRole);
        map.put("menuList",getLeafMonitortype(menuIdList));
        return JsonData.ok().setData(map);
    }
    /**
     * 根据树的根节点递归获取所有叶子节点
     * @param list
     * @return
     */
    public List<Long> getLeafMonitortype(List<SysMenuBean> list){
        List<Long> roleList = new ArrayList<>();
        list.forEach(data->{
            Optional<SysMenuBean> itemOptional = list.stream().filter(item -> item.getParentId().equals(data.getMenuId())).findFirst();
            if(itemOptional.isPresent()){
                return;
            }else{
                roleList.add(data.getMenuId());
            }
        });
        return roleList;
    }
    /**
    * 保存
    * @param sysRoleBean
    * @return JsonData
    */
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public JsonData save(@RequestBody SysRoleBean sysRoleBean){
		sysRoleService.save(sysRoleBean);
        return JsonData.ok();
    }

    /**
    * 修改
    * @param sysRoleBean
    * @return JsonData
    */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public JsonData update(@RequestBody SysRoleBean sysRoleBean){
		sysRoleService.update(sysRoleBean);
        return JsonData.ok();
    }

    /**
    * 删除
    * @param roleId
    * @return JsonData
    */
    @RequestMapping(value="/delete/{roleId}",method = RequestMethod.POST)
    public JsonData delete(@PathVariable("roleId") long roleId){
		sysRoleService.deleteBatch(roleId);
        return JsonData.ok();
    }

}

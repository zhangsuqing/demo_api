package cn.yoren.srs.demo.web.controller;

import java.util.*;

import javax.annotation.Resource;

import cn.yoren.srs.demo.common.entity.SysMenuBean;
import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.config.AdminContext;
import cn.yoren.srs.demo.core.service.ShiroService;
import cn.yoren.srs.demo.core.service.SysMenuService;
import cn.yoren.srs.demo.exception.RRException;
import cn.yoren.srs.demo.utils.Constant;
import cn.yoren.srs.demo.utils.ErrorEnum;
import cn.yoren.srs.demo.utils.JsonData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 菜单管理
 *
 * @author zsq
 * @date 2019-07-09 16:29:58
 */
@RequestMapping("/backend/menu")
@RestController
public class SysMenuController{

    @Resource
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单(路由)
     */
    @GetMapping("/nav")
    public JsonData nav(){
        SysUserBean admin  = AdminContext.get();
        if(admin == null){
            return JsonData.error(ErrorEnum.E_403,"登录失败");
        }
        List<SysMenuBean> menuList = sysMenuService.getUserMenuList(admin.getUserId());
//        Set<String> permissions = shiroService.getUserPermissions(1l);
        Map<String,Object> map = new HashMap<>();
        map.put("menuList", menuList);
        map.put("permissions", null);
        return JsonData.ok(map);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    public JsonData list(){
        List<SysMenuBean> menuList = sysMenuService.selectMenuList();
        for(SysMenuBean sysMenuEntity : menuList){
            SysMenuBean parentMenuEntity = sysMenuService.selectByPrimaryKey(sysMenuEntity.getParentId());
            if(parentMenuEntity != null){
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }else{
                sysMenuEntity.setParentName("一级菜单");
            }
        }
        return JsonData.ok(menuList);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    public JsonData select(){
        //查询列表数据
        List<SysMenuBean> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuBean root = new SysMenuBean();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return JsonData.ok().setData(menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    public JsonData info(@PathVariable("menuId") Long menuId){
        SysMenuBean menu = sysMenuService.selectByPrimaryKey(menuId);
        return JsonData.ok().setData(menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public JsonData save(@RequestBody SysMenuBean menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.insertMenu(menu);

        return JsonData.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody SysMenuBean menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.updateMenu(menu);

        return JsonData.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{menuId}")
    public JsonData delete(@PathVariable("menuId") long menuId){
        //判断是否有子菜单或按钮
        List<SysMenuBean> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return JsonData.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return JsonData.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuBean menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new RRException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenuBean parentMenu = sysMenuService.selectByPrimaryKey(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new RRException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new RRException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }
}

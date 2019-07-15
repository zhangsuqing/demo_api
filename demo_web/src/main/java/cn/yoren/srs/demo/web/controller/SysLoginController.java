package cn.yoren.srs.demo.web.controller;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.common.entity.form.SysLoginForm;
import cn.yoren.srs.demo.config.AdminContext;
import cn.yoren.srs.demo.core.service.SysCaptchaService;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.core.service.SysUserTokenService;
import cn.yoren.srs.demo.utils.JsonData;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author zsq
 */
@RequestMapping("/backend/admin")
@RestController
public class SysLoginController extends AbstractController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 验证码
	 */
	@GetMapping("/getCaptcha")
	public JsonData captcha(@RequestParam("uuid") String uuid){
		//获取图片验证码
		return JsonData.ok().setData(sysCaptchaService.getCaptcha(uuid));
	}

	/**
	 * 登录
	 */
	@PostMapping("/login")
	public JsonData login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return JsonData.error("验证码不正确");
		}

		//用户信息
		SysUserBean user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return JsonData.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return JsonData.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		HashMap<String, Object> map = sysUserTokenService.createToken(user.getUserId());
		user.setToken(map.get("token").toString());
		user.setExpire((int)map.get("expire"));
		return JsonData.ok(user);
	}


	/**
	 * 退出
	 */
	@PostMapping("/logout")
	public JsonData logout() {
		SysUserBean admin  = AdminContext.get();
		if(admin == null){
			return JsonData.ok();
		}else{
			sysUserTokenService.logout(admin.getUserId());
			return JsonData.ok();
		}
	}
	
}

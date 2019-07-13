package cn.yoren.srs.demo.config;


import cn.yoren.srs.demo.common.entity.SysUserBean;

public class AdminContext {

	private static ThreadLocal<SysUserBean> context = new ThreadLocal<>();

	public static void set(SysUserBean user) {
		context.set(user);
	}

	public static SysUserBean get() {
		return context.get();
	}

}

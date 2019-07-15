package cn.yoren.srs.demo.oauth2;

import cn.yoren.srs.demo.common.entity.SysUserBean;
import cn.yoren.srs.demo.common.entity.SysUserTokenBean;
import cn.yoren.srs.demo.config.AdminContext;
import cn.yoren.srs.demo.core.service.SysUserService;
import cn.yoren.srs.demo.core.service.SysUserTokenService;
import cn.yoren.srs.demo.utils.JsonData;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthWebInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	@Autowired
	private SysUserTokenService userTokenService;
	@Autowired
	private SysUserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object args) throws Exception {
		String token = request.getHeader("token");

		// token换取用户信息
		if (StringUtils.isNotBlank(token)) {
			SysUserTokenBean tokenBean = userTokenService.queryByToken(token);
			SysUserBean user = userService.selectByPrimaryKey(tokenBean.getUserId());
			if (user != null && tokenBean.getExpireTime().getTime() >= System.currentTimeMillis()) {
				userTokenService.updateTokenExpire(user.getUserId());
				AdminContext.set(user);
			} else {
				printJson(response,"");
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	private void returnJson(HttpServletResponse response, String json) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(json);
		} catch (IOException e) {
			logger.error("response error", e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	private static void printJson(HttpServletResponse response, String code) {
		JsonData jsonData = new JsonData().error("403","token过期,请重新登陆");
		String content = JSON.toJSONString(jsonData);
		printContent(response, content);
	}
	private static void printContent(HttpServletResponse response, String content) {
		try {
			response.reset();
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(content);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

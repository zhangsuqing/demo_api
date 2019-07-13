package cn.yoren.srs.demo.oauth2;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AccessConfiger extends WebMvcConfigurerAdapter {

	/**
	 * 解决全站跨域过滤器
	 */
	@Bean
	public FilterRegistrationBean corsFilterRegistration() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效

		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);

		return bean;
	}

	@Bean
	public AuthWebInterceptor authWebInterceptorTemp() {
	    return new AuthWebInterceptor();
	}

	/**
	 * 实现权限拦截
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 权限(用户)
		registry.addInterceptor(authWebInterceptorTemp()).addPathPatterns("/backend/**").excludePathPatterns("/backend/admin/**");
		super.addInterceptors(registry);
	}
}
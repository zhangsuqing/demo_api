package cn.yoren.srs.demo.web;

import cn.yoren.srs.demo.config.SpringContextUtil;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.yoren.srs.demo")
public class LawsonDemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LawsonDemoApplication.class, args);
		System.out.println("demo 启动成功profile=" + SpringContextUtil.getActiveProfile());
	}
}

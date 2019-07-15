package cn.yoren.srs.demo.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 * @author zsq
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}

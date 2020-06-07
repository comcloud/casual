package com.kuang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/7 21:02
 * @Version ：1.0
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //路由
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login.html").setViewName("login");
//        registry.addViewController("/main.html").setViewName("/fore/index");
    }

    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //addPathPatterns 要拦截的请求("/**")为所有
//        //excludePathPatterns 排除 "/login.html","/","/user/login","/css/*","/js/**","/img/**"
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/index", "/static/**");
////        "/css/*","/img/*","/js/**","/layui/**","/login/**","/static/**","/util/**"
//    }


}

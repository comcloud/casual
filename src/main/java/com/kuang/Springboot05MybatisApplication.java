package com.kuang;

import com.kuang.config.LoginHandlerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
public class Springboot05MybatisApplication extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //这是配置模板页面的路径  配置文件里面已经配置了  所以这里暂时不需要
        //这是是配置静态资源文件的路径
        registry.addResourceHandler("/static/**").
                addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //addPathPatterns 要拦截的请求("/**")为所有
//        //excludePathPatterns 排除 "/login.html","/","/user/login","/css/*","/js/**","/img/**"
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/index","/user/toLogin","/user/toRegister","/static/**");
////        "/css/*","/img/*","/js/**","/layui/**","/login/**","/static/**","/util/**"
//    }

    public static void main(String[] args) {
        SpringApplication.run(Springboot05MybatisApplication.class, args);
    }

}

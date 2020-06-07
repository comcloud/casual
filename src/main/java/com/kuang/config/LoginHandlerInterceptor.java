package com.kuang.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/8 18:52
 * @Version ：1.0
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    //目标方法执行之前(拦截器设置），再去注册
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //登录成功后，应该有用户的session
        Object user = request.getSession().getAttribute("userId");
        if(user == null){
            //未登陆，返回登陆页面
            request.setAttribute("msg","没有权限请先登陆");
            //转发这个request和response，返回到index.html
            request.getRequestDispatcher("templates/fore/login.html").forward(request,response);
            //不放行
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }
    }
}

//public class LoginHandlerInterceptor implements HandlerInterceptor {
//    //目标方法执行之前(拦截器设置），再去注册
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//                             Object handler) throws Exception {
//        //登录成功后，应该有用户的session
//        Object user = request.getSession().getAttribute("userId");
//        if(user == null){
//            //未登陆，返回登陆页面
//            request.setAttribute("msg","没有权限请先登陆");
//            //转发这个request和response，返回到index.html
//            request.getRequestDispatcher("/login.html").forward(request,response);
//            //不放行
//            return false;
//        }else{
//            //已登陆，放行请求
//            return true;
//        }
//
//    }
//
//
//}


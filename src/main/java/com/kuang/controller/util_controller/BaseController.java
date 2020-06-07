package com.kuang.controller.util_controller;

import javax.servlet.http.HttpSession;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/22 16:34
 * @Version ：1.0
 */

public class BaseController {

    /**
     * 判断用户是否已经登录
     * @param session 用于会话的接收
     * @return 返回取到user_Id属性的查找
     */
    public static Object checkUser(HttpSession session){
        Object object = session.getAttribute("userId");
        if(object==null){
            //用户未登录
            return null;
        }
        //用户已登录
        return object;
    }

}

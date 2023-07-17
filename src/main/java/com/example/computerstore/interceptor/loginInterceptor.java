package com.example.computerstore.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义一个拦截器
public class loginInterceptor implements HandlerInterceptor {
    //在调用所有处理请求的方法之前被自动调用执行的方法
    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，如果没有重定向到登录页面。
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Controller:映射)
     * @return true表示放行当前请求，false表示拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HttpServletRequest对象来获取session对象
        Object uid = request.getSession().getAttribute("uid");
        if(uid == null){
            //说明用户没有登录过系统，则利用响应对象重定向到login.html页面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        //q请求放行
        return true;
    }

    //在ModelAndView对象返回之后被调用的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //在整个请求所有关联的资源被执行完毕最后所执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

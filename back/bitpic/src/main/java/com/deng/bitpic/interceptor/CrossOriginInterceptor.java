package com.deng.bitpic.interceptor;

import com.deng.bitpic.utils.CrossOriginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 跨域拦截器
 * @author: Deng
 * @create: 2019-02-26
 */
@Slf4j
public class CrossOriginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 因为options预请求无法在aspect中拦截
         * 后期会有前端（网站和管理端）的跨域
         */
        CrossOriginUtil.responseSet(response, request.getHeader("Origin"));
        return true;
    }
}

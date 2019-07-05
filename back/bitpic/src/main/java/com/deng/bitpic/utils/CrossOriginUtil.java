package com.deng.bitpic.utils;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 跨域
 * @author: Deng
 * @create: 2019-02-23
 */
public class CrossOriginUtil {

    /**
     * 跨域响应设置
     * @param response response
     */
    public static void responseSet(HttpServletResponse response, String origin) {
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}

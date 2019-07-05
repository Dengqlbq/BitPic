package com.deng.bitpic.service;

/**
 * @description: 管理员
 * @author: Deng
 * @create: 2019-03-01
 */
public interface AdminService {

    /**
     * 校验信息
     * @param phone 手机号
     * @param password 密码
     * @return 结果
     */
    Boolean check(String phone, String password);
}

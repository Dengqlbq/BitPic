package com.deng.bitpic.service;

/**
 * @description: 短信验证码
 * @author: Deng
 * @create: 2019-02-02
 */
public interface SmsService {

    /**
     * 发送验证码
     * @param phone 手机号
     * @param type 验证码类型
     * @return 验证码
     */
    String sendCode(String phone, Integer type);
    /**
     * 生成验证码
     * @return 验证码
     */
    String createCode();
}

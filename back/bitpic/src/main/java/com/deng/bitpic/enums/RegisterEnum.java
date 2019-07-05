package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 注册类型
 * @author: Deng
 * @create: 2019-03-08
 */
@Getter
public enum RegisterEnum {

    /** 普通用户 */
    USER(0),

    /** 摄影师 */
    PHOTOGRAPHER(1);

    private Integer code;

    RegisterEnum(Integer code) {
        this.code = code;
    }}

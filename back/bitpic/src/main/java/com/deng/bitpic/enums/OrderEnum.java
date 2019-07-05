package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 订单
 * @author: Deng
 * @create: 2019-01-27
 */
@Getter
public enum OrderEnum {
    /** */

    STATUS_FINISHED("已完结", 0),

    STATUS_UNPAID("未支付", 1),

    STATUS_CANCELLED("已取消", 2),

    STATUS_REFUND("已退款", 3);

    private String message;

    private Integer code;

    OrderEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }}



package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 短信类型
 * @author: Deng
 * @create: 2019-02-27
 */
@Getter
public enum SmsEnum {
    /** */

    PHONE(0),

    PASSWORD(1),

    REGISTERED(2),

    PAY(3);

    private Integer code;

    SmsEnum(Integer code) {
        this.code = code;
    }
}

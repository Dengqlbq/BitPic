package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 用户信息（修改）
 * @author: Deng
 * @create: 2019-02-26
 */
@Getter
public enum UserInfoEnum {
    /** */

    PHONE(0),

    PASSWORD(1),

    NAME(2);
    
    private Integer code;

    UserInfoEnum(Integer code) {
        this.code = code;
    }
}

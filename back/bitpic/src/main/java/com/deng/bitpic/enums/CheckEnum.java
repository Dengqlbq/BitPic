package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 审核（身份，作品）
 * @author: Deng
 * @create: 2019-02-27
 */
@Getter
public enum CheckEnum {

    /** 等待审核 */
    STATUS_WAIT(0),

    /** 审核通过 */
    STATUS_ACCEPT(1),

    /** 审核失败 */
    STATUS_DENY(2);

    private Integer code;

    CheckEnum(Integer code) {
        this.code = code;
    }
}

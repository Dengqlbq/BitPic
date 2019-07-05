package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 作品信息
 * @author: Deng
 * @create: 2019-01-24
 */
// TODO constant
@Getter
public enum PicInfoEnum {
    /** */

    AUTHORIZATION_STANDARD("标准授权", 0),

    AUTHORIZATION_PLUS("扩展授权", 1),

    DIRECTION_HORIZONTAL("横向", 0),

    DIRECTION_VERTICAL("纵向", 1),

    STATUS_UP("上架", 0),

    STATUS_DOWN("下架", 1);

    private String message;

    private Integer code;

    PicInfoEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}

package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 文件
 * @author: Deng
 * @create: 2019-02-03
 */
@Getter
public enum FileEnum {

    /** 认证文件 */
    TYPE_AUTHENTICATE(2),

    /** 作品文件 */
    TYPE_PIC(0),

    /** 作品版权证明相关文件 */
    TYPE_PIC_CHECK(1),

    /** 头像文件 */
    TYPE_AVATAR(3),

    /** 图片文件 */
    TYPE_IMG(4),

    /** 所有文件类型 */
    TYPE_ALL(5);

    private Integer code;

    FileEnum(Integer code) {
        this.code = code;
    }}

package com.deng.bitpic.enums;

import lombok.Getter;

/**
 * @description: 操作结果
 * @author: Deng
 * @create: 2019-01-18
 */
@Getter
public enum ResultEnum {
    /** */

    LOGIN_SUCCESS("登陆成功", 0),

    LOGIN_FAIL("登陆失败", 1),

    REGISTER_SUCCESS("注册成功", 2),

    REGISTER_FAIL("注册失败", 3),

    OPERATION_SUCCESS("操作成功", 4),

    OPERATION_FAIL("操作失败", 5),

    STATUS_ERROR("状态错误", 6),

    PARAMS_ERROR("参数错误", 7),

    PERMISSION_DENY("权限不足", 8),

    BALANCE_INSUFFICIENT("余额不足", 9),

    FILE_NOT_EXISTS("文件不存在", 10),

    DIR_EMPTY("目录为空", 11),

    DIR_NOT_EXISTS("目录不存在", 12);

    private String message;

    private Integer code;

    ResultEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}

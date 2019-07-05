package com.deng.bitpic.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 用户信息（修改）
 * @author: Deng
 * @create: 2019-02-26
 */
@Data
public class UserInfoForm {

    @NotEmpty
    private String userId;

    private String phone;

    private String name;

    private String newPassword;

    @NotNull
    private Integer type;
}

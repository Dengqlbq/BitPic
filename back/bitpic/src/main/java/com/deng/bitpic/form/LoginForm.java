package com.deng.bitpic.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @description: 登陆表单
 * @author: Deng
 * @create: 2019-01-17
 */
@Data
public class LoginForm {

    /** 手机号 */
    @NotEmpty
    private String phone;

    /** 密码 */
    @NotEmpty
    @Size(min = 64, max = 64)
    private String password;
}

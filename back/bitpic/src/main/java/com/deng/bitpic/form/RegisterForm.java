package com.deng.bitpic.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description: 注册表单
 * @author: Deng
 * @create: 2019-01-20
 */
@Data
public class RegisterForm {

    @NotEmpty
    private String phone;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 64, max = 64)
    private String password;

    @NotNull
    private Integer type;
}

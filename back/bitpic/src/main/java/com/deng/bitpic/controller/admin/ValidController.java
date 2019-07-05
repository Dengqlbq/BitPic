package com.deng.bitpic.controller.admin;

import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.form.LoginForm;
import com.deng.bitpic.service.AdminService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description: 管理员认证
 * @author: Deng
 * @create: 2019-03-01
 */
@RestController
@RequestMapping("/admin/valid")
@Slf4j
public class ValidController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResultVO login(@Valid LoginForm form, BindingResult bindingResult) {
        boolean b, u;
        b = bindingResult.hasErrors();
        u = adminService.check(form.getPhone(), form.getPassword());

        if (b || !u) {
            throw new BitPicException(ResultEnum.LOGIN_FAIL);
        }
        // 管理系统部署在内网，无需过多身份校验
        return ResultVO.of(ResultEnum.LOGIN_SUCCESS);
    }
}

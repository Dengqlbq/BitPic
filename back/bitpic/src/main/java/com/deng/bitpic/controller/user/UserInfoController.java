package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.enums.UserInfoEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.form.UserInfoForm;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description: 用户信息
 * @author: Deng
 * @create: 2019-02-24
 */
@Slf4j
@RestController
@RequestMapping("/user/userInfo")
public class UserInfoController {
    // TODO 数据验证

    @Autowired
    private UserService userService;

    @PatchMapping("/update")
    public ResultVO phone(@Valid UserInfoForm userInfoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        User user = userService.findById(userInfoForm.getUserId());
        Integer type = userInfoForm.getType();

        if (type.equals(UserInfoEnum.NAME.getCode())) {
            user.setName(userInfoForm.getName());
        } else if (type.equals(UserInfoEnum.PHONE.getCode())) {
            user.setPhone(userInfoForm.getPhone());
        } else {
            user.setPassword(userInfoForm.getNewPassword());
        }

        userService.save(user);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

package com.deng.bitpic.controller.admin;

import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.SmsService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 短信验证码
 * @author: Deng
 * @create: 2019-02-03
 */
@RestController
@Slf4j
@RequestMapping("/admin/sms")
public class SmsController {
    // TODO 权限认证

    @Autowired
    private SmsService service;

    /**
     * 发送验证码
     * @param phone 手机号
     * @return 结果
     */
    @PostMapping("/sendCode")
    public ResultVO sendCode(@RequestParam("phone") String phone, @RequestParam("type") Integer type) {
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, service.sendCode(phone, type));
    }
}

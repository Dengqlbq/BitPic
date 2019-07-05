package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @description: 用户钱包
 * @author: Deng
 * @create: 2019-03-05
 */
@RestController
@RequestMapping("/user/wallet")
@Slf4j
public class WalletController {

    @Autowired
    private UserService userService;

    @PatchMapping("/recharge")
    public ResultVO recharge(@RequestParam("userId") String userId, @RequestParam("amount") BigDecimal amount) {
        userService.incrBalance(userId, amount);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    @PatchMapping("/cash")
    public ResultVO cash(@RequestParam("userId") String userId, @RequestParam("amount") BigDecimal amount) {
        userService.decrBalance(userId, amount);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

package com.deng.bitpic.controller.user;

import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @description: 充值
 * @author: Deng
 * @create: 2019-02-14
 */
@RestController
@RequestMapping("/user/recharge")
@Slf4j
public class RechargeController {

    @Autowired
    private UserService userService;

    /**
     * 用户充值
     * @param userId 用户ID
     * @param amount 金额（仅做演示，所以直接从前端传回）
     * @return 结果
     */
    @PutMapping("incr")
    public ResultVO incr(@RequestParam("userId") String userId, @RequestParam("amount") BigDecimal amount) {
        userService.incrBalance(userId, amount);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

package com.deng.bitpic.controller.user;

import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 点赞
 * @author: Deng
 * @create: 2019-02-14
 */
@RestController
@RequestMapping("/user/like")
@Slf4j
public class LikeController {

    @Autowired
    private PicInfoService picInfoService;

    /**
     * 作品点赞数增一
     * @param userId 用户ID（只是用于aspect中的身份验证）
     * @param number 作品编号
     * @return
     */
    @PatchMapping("/incr")
    public ResultVO incr(@RequestParam("userId") String userId, @RequestParam("number") String number) {
        picInfoService.incrLike(number);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

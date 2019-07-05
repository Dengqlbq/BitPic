package com.deng.bitpic.controller.admin;

import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.PicCheckService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-17
 */
@RestController
@RequestMapping("/admin/pic/check")
@Slf4j
public class PicCheckController {

    @Autowired
    private PicCheckService picCheckService;

    /**
     * 获取待审核作品最旧数据
     * @param page 分页
     * @return 结果
     */
    @GetMapping("/list/{page}")
    public ResultVO list(@PathVariable("page") Integer page) {
        // TODO 改进固定size
        List<PicCheck> list = picCheckService.getWaitingList(PageRequest.of(page, 5));
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, list);
    }

    /**
     * 作品通过审核
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    @PatchMapping("/accept")
    public ResultVO accept(@RequestParam("userId") String userId, @RequestParam("number") String number) {
        picCheckService.acceptPic(userId, number);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 作品未通过审核
     * @param number 作品编号
     * @param reason 原因
     * @return 结果
     */
    @PatchMapping("/deny")
    public ResultVO deny(@RequestParam("number") String number, @RequestParam("reason") String reason) {
        picCheckService.denyPic(number, reason);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

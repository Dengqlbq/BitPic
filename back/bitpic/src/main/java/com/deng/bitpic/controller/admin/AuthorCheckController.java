package com.deng.bitpic.controller.admin;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.AuthorCheckService;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-10
 */
@Slf4j
@RestController
@RequestMapping("/admin/author/check")
public class AuthorCheckController {

    @Autowired
    private AuthorCheckService authorCheckService;

    @Autowired
    private UserService userService;

    /**
     * 获取待审核作品最旧数据
     * @param page 分页
     * @return 结果
     */
    @GetMapping("/list/{page}")
    public ResultVO list(@PathVariable("page") Integer page) {
        List<AuthorCheck> list = authorCheckService.getWaitingList(PageRequest.of(page, 5));
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, list);
    }

    /**
     * 用户通过审核
     * @param userId 用户ID
     * @return 结果
     */
    @PatchMapping("/accept")
    public ResultVO accept(@RequestParam("userId") String userId) {
        User user = userService.findById(userId);
        user.setCertificated(true);
        userService.save(user);

        authorCheckService.acceptUser(userId);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 用户未通过审核
     * @param userId 用户ID
     * @param reason 原因
     * @return 结果
     */
    @PatchMapping("/deny")
    public ResultVO deny(@RequestParam("userId") String userId, @RequestParam("reason") String reason) {
        // 只在前端检查reason
        authorCheckService.denyUser(userId, reason);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

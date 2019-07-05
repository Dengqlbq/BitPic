package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.AuthorCheckService;
import com.deng.bitpic.service.PicCheckService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.vo.AuthorCheckVO;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 审核（身份，作品）
 * @author: Deng
 * @create: 2019-02-27
 */
@RestController
@RequestMapping("/user/check")
@Slf4j
public class CheckController {
    // TODO 完善

    @Autowired
    private AuthorCheckService authorCheckService;

    @Autowired
    private PicCheckService picCheckService;

    @GetMapping("/author/{userId}")
    public ResultVO author(@PathVariable("userId") String userId) {
        AuthorCheck authorCheck = authorCheckService.queryByUserId(userId);
        AuthorCheckVO vo = null;
        if (authorCheck != null) {
            vo = new AuthorCheckVO(authorCheck.getStatus() ,authorCheck.getReason(), authorCheck.getCreateTime());
        }
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, vo);
    }

    @GetMapping("/pic/{userId}/{status}/{page}")
    public ResultVO pic(@PathVariable("userId") String userId, @PathVariable("status") Integer status, @PathVariable("page") Integer page) {
        List<PicCheck> picCheckList = picCheckService.queryByUserIdAndStatusOrderByCreateTimeDesc(userId, status, PageRequest.of(page, 10));
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.picCheckList2PicCheckVOList(picCheckList));
    }
}

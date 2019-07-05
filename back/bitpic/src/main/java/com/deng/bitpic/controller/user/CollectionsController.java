package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.user.Collections;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.enums.PicInfoEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.CollectionsService;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-24
 */
@Slf4j
@RestController
@RequestMapping("/user/collections")
public class CollectionsController {

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private PicInfoService picInfoService;

    /**
     * 新增收藏
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    @PostMapping("/add")
    public ResultVO add(@RequestParam("userId") String userId, @RequestParam("number") String number) {
        PicInfo picInfo = picInfoService.queryByNumber(number);

        if (picInfo == null) {
            log.error("[用户收藏] 作品不存在 编号: {}", number);
            throw new BitPicException(ResultEnum.OPERATION_FAIL);
        } else if (picInfo.getStatus().equals(PicInfoEnum.STATUS_DOWN.getCode())) {
            log.error("[用户收藏] 作品已下架 编号: {}", number);
            throw new BitPicException(ResultEnum.STATUS_ERROR);
        } else {
            Collections collections = new Collections(KeyUtil.randomUUID(), userId, picInfo.getAuthorId(), number);
            collectionsService.save(collections);
            picInfoService.incrCollected(number);

            return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
        }
    }

    /**
     * 获取用户收藏
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/list/{userId}/{page}")
    public ResultVO list(@PathVariable("userId") String userId, @PathVariable("page") Integer page) {
        List<Collections> cl = collectionsService.queryByUserId(userId, PageRequest.of(page, 10));

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.collections2CollectionsVO(cl));
    }

    /**
     * 删除收藏
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    @DeleteMapping("/delete/{userId}/{number}")
    public ResultVO delete(@PathVariable("userId") String userId, @PathVariable("number") String number) {
        if (collectionsService.deleteByUserIdAndAndNumber(userId, number) == 0) {
            log.error("[用户收藏] 收藏不存在 userID: {} 作品编号: {}", userId, number);
            return ResultVO.of(ResultEnum.OPERATION_FAIL);
        }

        picInfoService.decrCollected(number);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 清空用户收藏
     * @param userId 用户ID
     * @return 结果
     */
    @DeleteMapping("/deleteAll/{userId}")
    private ResultVO deleteAll(@PathVariable("userId") String userId) {
        log.info("[用户收藏] 清空收藏 userID: {}", userId);

        List<String> numbers = collectionsService.queryByUserId(userId).stream().map(e -> e.getNumber()).collect(Collectors.toList());
        picInfoService.decrCollected(numbers);
        collectionsService.deleteByUserId(userId);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

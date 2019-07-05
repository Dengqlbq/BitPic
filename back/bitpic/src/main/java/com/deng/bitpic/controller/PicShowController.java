package com.deng.bitpic.controller;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 作品展示
 * @author: Deng
 * @create: 2019-01-12
 */
@Slf4j
@RestController
@RequestMapping("/picShow")
public class PicShowController {

    @Autowired
    private PicInfoService picInfoService;

    /**
     * 搜索结果，除keyword，其余参数用于过滤查询结果
     * @param keyword 关键词
     * @param format 图片格式
     * @param direction 图片方向
     * @param category 类别
     * @param group 是否套图
     * @param page 页数
     * @param size 每页大小
     * @return 搜索结果
     */
    // TODO: 改为form
    @GetMapping("/list")
    public ResultVO list(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "format", required = false) String format,
                         @RequestParam(value = "direction", required = false) Integer direction,
                         @RequestParam(value = "category", required = false) String category,
                         @RequestParam(value = "group", required = false) Boolean group,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "20") Integer size) {
        List<PicInfo> picInfoList = picInfoService.query(PageRequest.of(page, size), keyword, format, direction, category, group);

        if (picInfoList == null) {
            return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
        }
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.picInfo2PicInfoListVO(picInfoList));
    }

    /**
     * 作品详情
     * @param number 作品编号
     * @return 作品详情
     */
    @GetMapping("/detail/{number}")
    private ResultVO detail(@PathVariable String number) {
        PicInfo picInfo = picInfoService.queryByNumber(number);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.picInfo2PicInfoDetailVO(picInfo));
    }
}

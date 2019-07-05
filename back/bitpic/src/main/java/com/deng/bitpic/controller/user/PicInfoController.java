package com.deng.bitpic.controller.user;

import com.deng.bitpic.constant.KeywordConstant;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.form.PicInfoCreateForm;
import com.deng.bitpic.form.PicInfoUpdateForm;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 作品信息
 * @author: Deng
 * @create: 2019-02-12
 */
@RestController
@RequestMapping("/user/picInfo")
@Slf4j
public class PicInfoController {

    @Autowired
    private PicInfoService picInfoService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResultVO create(@Valid PicInfoCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        User user = userService.findById(form.getUserId());
        if (user == null) {
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        PicInfo picInfo = Converter.picInfoForm2PicInfo(form);
        picInfo.setNumber(KeyUtil.getKey(form.getSeed()));
        picInfo.setAuthorName(user.getName());
        picInfoService.save(picInfo);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    @GetMapping("/list/{userId}/{page}")
    public ResultVO list(@PathVariable("userId") String userId, @PathVariable("page") Integer page) {
        List<PicInfo> list = picInfoService.queryByAuthorId(userId, PageRequest.of(page, 10));
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.picInfoList2PicInfoAuthorVOList(list));
    }

    @PatchMapping("/update")
    public ResultVO update(@Valid PicInfoUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        PicInfo picInfo = picInfoService.queryByNumber(form.getNumber());
        picInfo.setPriceStandard(form.getPriceStandard());
        picInfo.setPricePlus(form.getPricePlus());
        picInfo.setKeywords(form.getKeywords().split(KeywordConstant.SPLIT_REGEX));
        picInfo.setStatus(form.getStatus());

        picInfoService.save(picInfo);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

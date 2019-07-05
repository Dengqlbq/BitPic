package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.user.Wish;
import com.deng.bitpic.dto.WishDTO;
import com.deng.bitpic.enums.PicInfoEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.service.WishService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.ResultVO;
import com.deng.bitpic.vo.WishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-25
 */
@Slf4j
@RestController
@RequestMapping("/user/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @Autowired
    private PicInfoService picInfoService;

    /**
     * 新增心愿
     * @param userId 用户ID
     * @param number 作品编号
     * @param type 授权类型
     * @return 结果
     */
    @PostMapping("/add")
    public ResultVO add(@RequestParam("userId") String userId, @RequestParam("number") String number, @RequestParam("type") Integer type) {
        PicInfo picInfo = picInfoService.queryByNumber(number);

        if (picInfo == null) {
            log.error("[用户心愿] 作品不存在 编号: {}", number);
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        } else if (picInfo.getStatus().equals(PicInfoEnum.STATUS_DOWN.getCode())) {
            log.error("[用户心愿] 作品已下架 编号: {}");
            throw new BitPicException(ResultEnum.STATUS_ERROR);
        } else {
            String uuid = KeyUtil.randomUUID();
            BigDecimal price;
            if (type.equals(PicInfoEnum.AUTHORIZATION_STANDARD.getCode())) {
                price = picInfo.getPriceStandard();
            } else {
                price = picInfo.getPricePlus();
            }

            Wish wish = new Wish(uuid, userId, picInfo.getAuthorId(), number, type, price);
            wishService.save(wish);

            return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
        }
    }

    /**
     * 进入结算页面（提交订单前）获取所选心愿作品的对应授权类型最新价格，同时更新到用户心愿表（应对最终不提交订单的情况）
     * @param userId 用户ID
     * @param wishListString WishDTOList的JSON字符串
     * @return 最新价格
     */
    @PostMapping("/update")
    public ResultVO update(@RequestParam("userId") String userId, @RequestParam("wishList") String wishListString) {
        List<WishDTO> wishDTOList = Converter.wishListString2WishDTOList(wishListString);
        List<WishVO> wishVOList = new ArrayList<>();
        // 遍历结算时所选心愿作品
        for (WishDTO dto : wishDTOList) {
            Wish wish = wishService.queryByUserIdAndNumber(userId, dto.getNumber());
            PicInfo picInfo = picInfoService.queryByNumber(dto.getNumber());
            if (wish != null && picInfo != null) {
                // 获取最新价格
                BigDecimal price;
                if (picInfo.getStatus().equals(PicInfoEnum.STATUS_DOWN.getCode())) {
                    price = new BigDecimal(-1);
                } else if (dto.getType().equals(PicInfoEnum.AUTHORIZATION_STANDARD.getCode())) {
                    price = picInfo.getPriceStandard();
                } else {
                    price = picInfo.getPricePlus();
                }
                // 更新用户心愿
                if (!price.equals(wish.getPrice())) {
                    wish.setPrice(price);
                    wish.setType(dto.getType());
                    wishService.save(wish);
                }
                wishVOList.add(new WishVO(picInfo.getAuthorId(), dto.getNumber(), dto.getType(), price));
            }
        }

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, wishVOList);
    }

    /**
     * 获取用户心愿
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/list/{userId}/{page}")
    public ResultVO list(@PathVariable("userId") String userId, @PathVariable("page") Integer page) {
        List<Wish> wl = wishService.queryByUserId(userId, PageRequest.of(page, 10));

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, Converter.wish2WishVO(wl));
    }

    /**
     * 删除用户心愿
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    @DeleteMapping("delete/{userId}/{number}")
    public ResultVO delete(@PathVariable("userId") String userId, @PathVariable("number") String number) {
        if (wishService.deleteByUserIdAndAndNumber(userId, number) == 0) {
            log.error("[用户心愿] 心愿不存在 作品编号: {}", number);
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 清空用户心愿
     * @param userId 用户ID
     * @return 结果
     */
    @DeleteMapping("/deleteAll/{userId}")
    public ResultVO deleteAll(@PathVariable("userId") String userId) {
        log.info("[用户心愿] 清空心愿 用户ID: {}", userId);
        wishService.deleteByUserId(userId);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }
}

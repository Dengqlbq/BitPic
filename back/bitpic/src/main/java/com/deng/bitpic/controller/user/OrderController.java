package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.user.OrderDetail;
import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.dataobject.user.Wish;
import com.deng.bitpic.dto.WishDTO;
import com.deng.bitpic.enums.OrderEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.*;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.OrderMasterVO;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户订单（主表+详情）
 * @author: Deng
 * @create: 2019-01-27
 */
@RestController
@Slf4j
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private WishService wishService;

    @Autowired
    private PicInfoService picInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param userId 用户ID
     * @param wishListString WishDTOList的JSON字符串
     * @return 结果
     */
    @PostMapping("/create")
    public ResultVO create(@RequestParam("userId") String userId, @RequestParam("wishList") String wishListString) {
        List<WishDTO> wishDTOList = Converter.wishListString2WishDTOList(wishListString);

        String masterId = KeyUtil.randomUUID();
        BigDecimal total = new BigDecimal(0);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<Wish> deleteWishList = new ArrayList<>();

        // 遍历选中的心愿作品
        for (WishDTO dto : wishDTOList) {
            PicInfo picInfo = picInfoService.queryByNumber(dto.getNumber());
            Wish wish = wishService.queryByUserIdAndNumber(userId, dto.getNumber());

            if (picInfo != null && wish != null) {
                /**
                 * 作品信息必须从心愿表中获取（即不能获取作品的最新信息而必须用结算时更新进心愿表的信息）
                 * 可能结算后用户犹豫很久而不提交订单（结算页面不关闭），犹豫期间作品可能被作者下架，或者修改价格
                 * 但都以结算时更新进心愿表的信息为准，对作者方面而言可将相关条款写进协议
                 */
                OrderDetail orderDetail = new OrderDetail();
                BeanUtils.copyProperties(wish, orderDetail);
                orderDetail.setId(KeyUtil.randomUUID());
                orderDetail.setMasterId(masterId);
                orderDetail.setCreateTime(new Date());

                orderDetailList.add(orderDetail);
                deleteWishList.add(wish);

                total =total.add(wish.getPrice());
            } else {
                log.error("[用户订单] 创建订单失败 用户ID: {}", userId);
                throw new BitPicException(ResultEnum.PARAMS_ERROR);
            }
        }

        // 订单信息入库
        OrderMaster orderMaster = new OrderMaster(masterId, userId, total, new Date(), new Date());
        orderMasterService.save(orderMaster);
        orderDetailService.saveAll(orderDetailList);

        // 删除选定心愿
        wishService.deleteAll(deleteWishList);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    @GetMapping("/list/{userId}/{status}/{page}")
    public ResultVO list(@PathVariable("userId") String userId, @PathVariable("status") Integer status, @PathVariable("page") Integer page) {
        List<OrderMasterVO> list = orderMasterService.queryByUserIdAndStatus(userId, status, PageRequest.of(page, 10));
        if (list.size() == 0) {
            return ResultVO.of(ResultEnum.OPERATION_SUCCESS, null);
        }

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS, list);
    }

    /**
     * 取消订单
     * @param userId 用户ID
     * @param orderId 订单主表ID
     * @return 结果
     */
    @PatchMapping("/cancel")
    public ResultVO cancel(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId) {
        OrderMaster orderMaster = orderMasterService.queryById(orderId);
        if (orderMaster == null ) {
            log.error("[用户订单] 取消订单失败 用户ID: {} 订单ID: {}", userId, orderId);
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        orderMaster.setStatus(OrderEnum.STATUS_CANCELLED.getCode());
        orderMasterService.save(orderMaster);
        
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 完结订单
     * @param userId 用户ID
     * @param orderId 订单主表ID
     * @return 结果
     */
    @PatchMapping("/finish")
    public ResultVO finish(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId) {
        if (!orderService.check(userId, orderId)) {
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }

        OrderMaster orderMaster = orderMasterService.queryById(orderId);
        User user = userService.findById(userId);


        if (orderMaster.getTotal().compareTo(user.getBalance()) > 0) {
            throw new BitPicException(ResultEnum.BALANCE_INSUFFICIENT);
        }

        userService.decrBalance(userId, orderMaster.getTotal());
        orderMaster.setStatus(OrderEnum.STATUS_FINISHED.getCode());
        orderMasterService.save(orderMaster);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    @PatchMapping("/refund")
    public ResultVO refund(@RequestParam("userId") String userId, @RequestParam("orderId") String orderId) {
        if (!orderService.check(userId, orderId)) {
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }

        OrderMaster orderMaster = orderMasterService.queryById(orderId);
        if (orderMaster.getHadDownload()) {
            throw new BitPicException(ResultEnum.STATUS_ERROR);
        }

        userService.incrBalance(userId, orderMaster.getTotal());
        orderMaster.setStatus(OrderEnum.STATUS_REFUND.getCode());
        orderMasterService.save(orderMaster);
        throw new BitPicException(ResultEnum.OPERATION_SUCCESS);
    }
}

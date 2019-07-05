package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.OrderDetail;
import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.OrderMasterService;
import com.deng.bitpic.service.OrderService;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 订单
 * @author: Deng
 * @create: 2019-03-12
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private UserService userService;

    @Autowired
    private PicInfoService picInfoService;

    @Override
    public Boolean check(String userId, String masterId) {
        OrderMaster master = orderMasterService.queryById(masterId);
        if (master == null) {
            return false;
        }
        return master.getUserId().equals(userId);
    }

    @Override
    public void executeDetail(OrderDetail detail) {
        userService.incrBalance(detail.getAuthorId(), detail.getPrice());
        picInfoService.incrSale(detail.getNumber(), detail.getType());
    }
}

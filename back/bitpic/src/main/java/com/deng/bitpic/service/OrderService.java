package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.OrderDetail;

/**
 * @description: 订单
 * @author: Deng
 * @create: 2019-03-12
 */
public interface OrderService {

    /**
     * 检查用户与订单的关联
     * @param userId 用户ID
     * @param masterId 订单主表ID
     * @return
     */
    Boolean check(String userId, String masterId);

    /**
     * 处理订单详情相关操作
     * 作者余额及作品信息
     * @param detail 订单详情
     */
    void executeDetail(OrderDetail detail);
}

package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.OrderDetail;

import java.util.List;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2019-01-27
 */
public interface OrderDetailService {

    /**
     * 保存，更新
     * @param orderDetail 对象
     * @return 对象
     */
    OrderDetail save(OrderDetail orderDetail);

    /**
     * 根据主表ID查询
     * @param masterId 主表ID
     * @return 结果
     */
    List<OrderDetail> queryByMasterId(String masterId);

    /**
     * 保存，更新所有给定对象
     * @param orderDetailList 对象列表
     */
    void saveAll(List<OrderDetail> orderDetailList);
}

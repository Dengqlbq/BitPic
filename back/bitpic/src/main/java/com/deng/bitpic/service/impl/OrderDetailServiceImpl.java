package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.OrderDetail;
import com.deng.bitpic.repository.OrderDetailRepository;
import com.deng.bitpic.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2019-01-27
 */
@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> queryByMasterId(String masterId) {
        return repository.queryByMasterId(masterId);
    }

    @Override
    public void saveAll(List<OrderDetail> orderDetailList) {
        repository.saveAll(orderDetailList);
    }
}

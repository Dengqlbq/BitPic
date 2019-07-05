package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.OrderDetail;
import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.repository.OrderMasterRepository;
import com.deng.bitpic.service.OrderDetailService;
import com.deng.bitpic.service.OrderMasterService;
import com.deng.bitpic.vo.OrderDetailVO;
import com.deng.bitpic.vo.OrderMasterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2019-01-27
 */
@Slf4j
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository repository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public OrderMaster save(OrderMaster orderMaster) {
        return repository.save(orderMaster);
    }

    @Override
    public List<OrderMaster> queryByUserId(String userId, Pageable pageable) {
        return repository.queryByUserId(userId, pageable).getContent();
    }

    @Override
    public OrderMaster queryById(String id) {
        return repository.queryById(id);
    }

    @Override
    public List<OrderMasterVO> queryByUserIdAndStatus(String userId, Integer status, Pageable pageable) {
        List<OrderMaster> orderMasterList = repository.queryByUserIdAndStatus(userId, status, pageable).getContent();
        List<OrderMasterVO> voList = new ArrayList<>();

        for (OrderMaster master : orderMasterList) {
            OrderMasterVO vo = new OrderMasterVO();
            BeanUtils.copyProperties(master, vo);

            List<OrderDetail> orderDetailList = orderDetailService.queryByMasterId(master.getId());
            List<OrderDetailVO> detailVOList = new ArrayList<>();
            for (OrderDetail detail : orderDetailList) {
                OrderDetailVO v = new OrderDetailVO();
                BeanUtils.copyProperties(detail, v);

                detailVOList.add(v);
            }

            vo.setOrderDetails(detailVOList);
            voList.add(vo);
        }

        return voList;
    }
}

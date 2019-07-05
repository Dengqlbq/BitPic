package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.vo.OrderMasterVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2019-01-27
 */
public interface OrderMasterService {

    /**
     * 保存，更新
     * @param orderMaster 对象
     * @return 结果
     */
    OrderMaster save(OrderMaster orderMaster);

    /**
     * 根据用户ID查询
     * @param userId 用户ID
     * @param pageable 分页
     * @return 结果
     */
    List<OrderMaster> queryByUserId(String userId, Pageable pageable);

    /**
     * 根据订单ID查询
     * @param id 订单ID
     * @return 结果
     */
    OrderMaster queryById(String id);

    /**
     * 根据用户ID和订单状态查找
     * @param userId 用户ID
     * @param status 状态
     * @param pageable 分页
     * @return 结果
     */
    List<OrderMasterVO> queryByUserIdAndStatus(String userId, Integer status, Pageable pageable);
}

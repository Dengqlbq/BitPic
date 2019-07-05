package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2019-01-27
 */
public interface OrderMasterRepository extends ElasticsearchCrudRepository<OrderMaster, String> {

    /**
     * 根据用户ID查询
     * @param userId 用户ID
     * @param pageable 分页
     * @return 结果
     */
    Page<OrderMaster> queryByUserId(String userId, Pageable pageable);

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
    Page<OrderMaster> queryByUserIdAndStatus(String userId, Integer status, Pageable pageable);
}

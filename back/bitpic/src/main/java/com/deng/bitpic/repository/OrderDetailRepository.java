package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.OrderDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2019-01-27
 */
public interface OrderDetailRepository extends ElasticsearchCrudRepository<OrderDetail, String> {

    /**
     * 根据主表ID查询
     * @param masterId 主表ID
     * @return 结果
     */
    List<OrderDetail> queryByMasterId(String masterId);
}

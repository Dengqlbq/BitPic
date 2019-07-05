package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-23
 */
public interface CollectionsRepository extends ElasticsearchCrudRepository<Collections, String> {

    /**
     * 根据用户Id查询
     * @param userId 用户Id
     * @param pageable 分页
     * @return 结果
     */
    Page<Collections> queryByUserId(String userId, Pageable pageable);

    /**
     * 获取用户所有收藏
     * @param userId 用户ID
     * @return 结果
     */
    List<Collections> queryByUserId(String userId);

    /**
     * 根据用户Id删除
     * @param userId 用户Id
     * @return 删除数量
     */
    Integer deleteByUserId(String userId);

    /**
     * 根据用户Id和作品编号删除
     * @param userId 用户Id
     * @param number 作品编号
     * @return 删除数量
     */
    Integer deleteByUserIdAndNumber(String userId, String number);
}

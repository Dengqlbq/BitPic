package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-23
 */
public interface WishRepository extends ElasticsearchCrudRepository<Wish, String> {

    /**
     * 根据用户ID和作品编号查询
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    Wish queryByUserIdAndAndNumber(String userId, String number);

    /**
     * 根据用户ID查询
     * @param userId 用户ID
     * @param pageable 分页
     * @return 结果
     */
    Page<Wish> queryByUserId(String userId, Pageable pageable);

    /**
     * 根据用户ID删除
     * @param userId 用户ID
     * @return 删除数量
     */
    Integer deleteByUserId(String userId);

    /**
     * 根据用户ID和作品编号删除
     * @param userId 用户ID
     * @param number 作品编号
     * @return 删除数量
     */
    Integer deleteByUserIdAndNumber(String userId, String number);

    /**
     * 查询用户心愿单作品数量
     * @param userId 用户ID
     * @return 数量
     */
    Integer countByUserId(String userId);
}

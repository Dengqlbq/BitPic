package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.Wish;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-23
 */
public interface WishService {

    /**
     * 保存，更新
     * @param wish 对象
     * @return 对象
     */
    Wish save(Wish wish);

    /**
     * 根据用户ID和作品编号查询
     * @param userId 用户ID
     * @param number 作品编号
     * @return 结果
     */
    Wish queryByUserIdAndNumber(String userId, String number);

    /**
     * 根据用户ID查询
     * @param userId 用户ID
     * @param pageable 分页
     * @return 结果
     */
    List<Wish> queryByUserId(String userId, Pageable pageable);

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
    Integer deleteByUserIdAndAndNumber(String userId, String number);

    /**
     * 删除所有给定对象
     * @param wishList 对象列表
     */
    void deleteAll(List<Wish> wishList);

    /**
     * 查询用户心愿单作品数量
     * @param userId 用户ID
     * @return 数量
     */
    Integer countByUserId(String userId);
}

package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.Wish;
import com.deng.bitpic.repository.WishRepository;
import com.deng.bitpic.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-23
 */
@Service
@Slf4j
public class WishServiceImpl implements WishService {

    @Autowired
    private WishRepository repository;

    @Override
    public Wish save(Wish wish) {
        return repository.save(wish);
    }

    @Override
    public Wish queryByUserIdAndNumber(String userId, String number) {
        return repository.queryByUserIdAndAndNumber(userId, number);
    }

    @Override
    public List<Wish> queryByUserId(String userId, Pageable pageable) {
        return repository.queryByUserId(userId, pageable).getContent();
    }

    @Override
    public Integer deleteByUserId(String userId) {
        return repository.deleteByUserId(userId);
    }

    @Override
    public Integer deleteByUserIdAndAndNumber(String userId, String number) {
        return repository.deleteByUserIdAndNumber(userId, number);
    }

    @Override
    public void deleteAll(List<Wish> wishList) {
        repository.deleteAll(wishList);
    }

    @Override
    public Integer countByUserId(String userId) {
        return repository.countByUserId(userId);
    }
}

package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.Collections;
import com.deng.bitpic.repository.CollectionsRepository;
import com.deng.bitpic.service.CollectionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-23
 */
@Service
@Slf4j
public class CollectionsServiceImpl implements CollectionsService {

    @Autowired
    private CollectionsRepository repository;

    @Override
    public Collections save(Collections collections) {
        return repository.save(collections);
    }

    @Override
    public List<Collections> queryByUserId(String userId, Pageable pageable) {
        return repository.queryByUserId(userId, pageable).getContent();
    }

    @Override
    public List<Collections> queryByUserId(String userId) {
        return repository.queryByUserId(userId);
    }

    @Override
    public Integer deleteByUserId(String userId) {
        return repository.deleteByUserId(userId);
    }

    @Override
    public Integer deleteByUserIdAndAndNumber(String userId, String number) {
        return repository.deleteByUserIdAndNumber(userId, number);
    }
}

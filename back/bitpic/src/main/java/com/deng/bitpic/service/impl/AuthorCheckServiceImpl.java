package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.enums.CheckEnum;
import com.deng.bitpic.repository.AuthorCheckRepository;
import com.deng.bitpic.service.AuthorCheckService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-10
 */
@Service
public class AuthorCheckServiceImpl implements AuthorCheckService {

    @Autowired
    private AuthorCheckRepository repository;

    @Override
    public AuthorCheck save(AuthorCheck authorCheck) {
        return repository.save(authorCheck);
    }

    @Override
    public List<AuthorCheck> getWaitingList(Pageable pageable) {
        return repository.queryByStatusOrderByCreateTimeAsc(CheckEnum.STATUS_WAIT.getCode(), pageable).getContent();
    }

    @Override
    public AuthorCheck queryByUserId(String userId) {
        return repository.queryByUserId(userId);
    }

    @Override
    public Integer deleteByUserId(String userId) {
        return repository.deleteByUserId(userId);
    }

    @Override
    public void acceptUser(String userId) {
        executeUser(userId, CheckEnum.STATUS_ACCEPT.getCode(), null);
    }

    @Override
    public void denyUser(String userId, String reason) {
        executeUser(userId, CheckEnum.STATUS_DENY.getCode(), reason);
    }

    @Override
    public void executeUser(String userId, Integer status, String reason) {
        AuthorCheck authorCheck = queryByUserId(userId);
        if (authorCheck != null) {
            authorCheck.setStatus(status);
            if (reason != null) {
                authorCheck.setReason(reason);
            }
            authorCheck.setUpdateTime(new Date());
            save(authorCheck);
        }
    }
}

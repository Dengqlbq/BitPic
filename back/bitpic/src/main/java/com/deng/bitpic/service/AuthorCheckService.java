package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-10
 */
public interface AuthorCheckService {

    /**
     * 保存，更新
     * @param authorCheck 对象
     * @return 对象
     */
    AuthorCheck save(AuthorCheck authorCheck);

    /**
     * 获取最旧等待认证列表
     * @param pageable 分页
     * @return 结果
     */
    List<AuthorCheck> getWaitingList(Pageable pageable);

    /**
     * 根据用户ID查找
     * @param userId 用户ID
     * @return 结果
     */
    AuthorCheck queryByUserId(String userId);

    /**
     * 根据用户ID删除
     * @param userId 用户ID
     * @return 删除数量
     */
    Integer deleteByUserId(String userId);

    /**
     * 认证通过
     * @param userId 用户ID
     */
    void acceptUser(String userId);

    /**
     * 认证失败
     * @param userId 用户ID
     * @param reason 原因
     */
    void denyUser(String userId, String reason);

    /**
     * 处理用户
     * @param userId 用户ID
     * @param status 认证状态
     * @param reason 认证失败原因
     */
    void executeUser(String userId, Integer status, String reason);
}

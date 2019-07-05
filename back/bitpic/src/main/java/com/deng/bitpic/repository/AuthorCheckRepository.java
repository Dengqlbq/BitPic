package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-08
 */
public interface AuthorCheckRepository extends ElasticsearchCrudRepository<AuthorCheck, String> {

    /**
     * 根据认证状态获取最旧数据
     * @param status 审核状态
     * @param pageable 分页
     * @return 结果
     */
    Page<AuthorCheck> queryByStatusOrderByCreateTimeAsc(Integer status, Pageable pageable);

    /**
     * 根据用户ID查询
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
}

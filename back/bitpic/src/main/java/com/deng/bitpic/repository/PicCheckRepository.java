package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.PicCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-10
 */
public interface PicCheckRepository extends ElasticsearchCrudRepository<PicCheck, String> {

    /**
     * 根据审核状态获取最旧数据
     * @param status 审核状态
     * @param pageable 分页
     * @return 结果
     */
    Page<PicCheck> queryByStatusOrderByCreateTimeAsc(Integer status, Pageable pageable);

    /**
     * 根据作品编号查找
     * @param number 作品编号
     * @return 结果
     */
    PicCheck queryByNumber(String number);

    /**
     * 根据用户ID和审核状态查询
     * @param userId 用户ID
     * @param status 审核状态
     * @param pageable 分页
     * @return 结果
     */
    Page<PicCheck> queryByUserIdAndStatusOrderByCreateTimeDesc(String userId, Integer status, Pageable pageable);
}

package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.PicInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @description: 图片作品
 * @author: Deng
 * @create: 2019-01-07
 */

public interface PicInfoRepository extends ElasticsearchCrudRepository<PicInfo, String> {

    /**
     * 根据关键词和审核状态查找
     * @param keyword 关键词
     * @param checkStatus 审核状态
     * @param pageable 分页
     * @return 结果
     */
    Page<PicInfo> queryPicInfoByKeywordsMatchesAndCheckStatus(String keyword, Integer checkStatus, Pageable pageable);

    /**
     * 根据作者和审核状态查找
     * @param userId 用户ID（作者）
     * @param checkStatus 审核状态
     * @param pageable 分页
     * @return 结果
     */
    Page<PicInfo> queryByAuthorIdAndCheckStatus(String userId, Integer checkStatus, Pageable pageable);

    /**
     * 根据编号查找
     * @param number 作品编号
     * @return 结果
     */
    PicInfo queryByNumber(String number);

    /**
     * 根据编号删除
     * @param number 作品编号
     * @return 删除数量
     */
    Integer deleteByNumber(String number);
}

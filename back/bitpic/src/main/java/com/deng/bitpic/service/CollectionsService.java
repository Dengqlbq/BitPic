package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.Collections;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-23
 */
public interface CollectionsService {

    /**
     * 保存，更新
     * @param collections 对象
     * @return 对象
     */
    Collections save(Collections collections);

    /**
     * 根据用户Id查询
     * @param userId 用户Id
     * @param pageable 分页
     * @return 结果
     */
    List<Collections> queryByUserId(String userId, Pageable pageable);

    /**
     * 根据用户ID获取所有
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
    Integer deleteByUserIdAndAndNumber(String userId, String number);
}

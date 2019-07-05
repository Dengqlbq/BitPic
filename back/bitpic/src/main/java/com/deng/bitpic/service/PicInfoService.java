package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.PicInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 图片作品
 * @author: Deng
 * @create: 2019-01-11
 */
public interface PicInfoService {

    /**
     * 保存，更新
     * @param picInfo 对象
     * @return 对象
     */
    PicInfo save(PicInfo picInfo);

    /**
     * @param userId 用户ID（作者）
     * @param pageable 分页
     * @return 结果
     */
    List<PicInfo> queryByAuthorId(String userId, Pageable pageable);

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

    /**
     * 根据关键词查找
     * @param keyword 关键词
     * @param pageable 分页
     * @return 结果
     */
    List<PicInfo> queryByKeywordsMatches(String keyword, Pageable pageable);

    /**
     * 主查询方法
     * @param pageable 分页
     * @param keyword 关键词
     * @param format 格式
     * @param direction 方向
     * @param category 类别
     * @param group 套图
     * @return 结果
     */
    List<PicInfo> query(Pageable pageable, String keyword, String format, Integer direction, String category, Boolean group);

    /**
     * 根据参数过滤查询结果
     * @param picInfoList 根据关键词的查询结果
     * @param format 格式
     * @param direction 方向
     * @param category 类别
     * @param group 套图
     * @return 过滤结果
     */
    List<PicInfo> filter(List<PicInfo> picInfoList, String format, Integer direction, String category, Boolean group);

    /**
     * 点赞数增一
     * @param number 作品编号
     */
    void incrLike(String number);

    /**
     * 收藏数增一
     * @param number 作品编号
     */
    void incrCollected(String number);

    /**
     * 收藏数减一
     * @param number 作品编号
     */
    void decrCollected(String number);

    /**
     * 收藏数减一
     * @param numbers 作品编号数组
     */
    void decrCollected(List<String> numbers);

    /**
     * 相应授权类型出售分数增一
     * @param number 作品编号
     * @param type 授权类型
     */
    void incrSale(String number, Integer type);
}

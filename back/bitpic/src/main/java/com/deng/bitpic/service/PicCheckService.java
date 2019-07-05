package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.admin.PicCheck;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-10
 */
public interface PicCheckService {

    /**
     * 保存，更新
     * @param picCheck 对象
     * @return 对象
     */
    PicCheck save(PicCheck picCheck);

    /**
     * 根据编号查询
     * @param number 作品编号
     * @return 对象
     */
    PicCheck queryByNumber(String number);

    /**
     * 获取最旧等待认证列表
     * @param pageable 分页
     * @return 结果
     */
    List<PicCheck> getWaitingList(Pageable pageable);


    /**
     * 审核通过
     * @param userId 用户ID
     * @param number 作品编号
     */
    void acceptPic(String userId, String number);

    /**
     * 审核失败
     * @param number 作品编号
     * @param reason 原因
     */
    void denyPic(String number, String reason);

    /**
     * 处理作品审核
     * @param number 作品编号
     * @param status 审核状态
     * @param reason 失败原因
     */
    void executePic(String number, Integer status, String reason);

    /**
     * 根据用户ID和审核状态查找
     * @param userId 用户ID
     * @param status 审核状态
     * @param pageable 分页
     * @return 结果
     */
    List<PicCheck> queryByUserIdAndStatusOrderByCreateTimeDesc(String userId, Integer status, Pageable pageable);
}

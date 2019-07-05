package com.deng.bitpic.service;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.vo.UserVO;

import java.math.BigDecimal;

/**
 * @description: 用户（包括摄影师）
 * @author: Deng
 * @create: 2019-01-17
 */
public interface UserService {

    /**
     * 保存，更新
     * @param user 对象
     * @return 操作后对象
     */
    User save(User user);

    /**
     * 根据id查找
     * @param id 用户id
     * @return 结果
     */
    User findById(String id);

    /**
     * 根据邮箱查找
     * @param phone 手机号
     * @return 结果
     */
    User findByPhone(String phone);

    /**
     * 根据id删除
     * @param id 用户id
     */
    void deleteById(String id);

    /**
     * 增加用户余额
     * @param id 用户ID
     * @param amount 金额
     */
    void incrBalance(String id, BigDecimal amount);

    /**
     * 减少用户余额
     * @param id 用户ID
     * @param amount 金额
     */
    void decrBalance(String id, BigDecimal amount);

    /**
     * 检查用户信息是否正确
     * @param phone 手机号
     * @param password 密码
     * @return 结果
     */
    Boolean check(String phone, String password);

    /**
     * 根据手机号返回用户数据
     * @param phone 手机号
     * @return UserVO
     */
    UserVO createUserVO(String phone);
}

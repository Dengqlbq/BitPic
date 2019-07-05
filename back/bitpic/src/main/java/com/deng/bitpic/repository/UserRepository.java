package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 用户（包括摄影师）
 * @author: Deng
 * @create: 2019-01-17
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据手机查找
     * @param phone 手机号
     * @return 结果
     */
    User findByPhone(String phone);
}

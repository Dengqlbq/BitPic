package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 管理员
 * @author: Deng
 * @create: 2019-03-01
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

    /**
     * 根据手机号查找
     * @param phone 管理员手机
     * @return 结果
     */
    Admin findByPhone(String phone);
}

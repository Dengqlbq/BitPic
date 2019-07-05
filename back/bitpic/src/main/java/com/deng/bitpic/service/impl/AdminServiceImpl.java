package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.admin.Admin;
import com.deng.bitpic.repository.AdminRepository;
import com.deng.bitpic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 管理员
 * @author: Deng
 * @create: 2019-03-01
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Boolean check(String phone, String password) {
        Admin admin = adminRepository.findByPhone(phone);
        return admin != null && admin.getPassword().equals(password);
    }
}

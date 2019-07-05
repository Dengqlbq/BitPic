package com.deng.bitpic.service.impl;

import com.deng.bitpic.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void check() {
        Assert.assertTrue(adminService.check("15622472033", "123456"));
    }
}
package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.Admin;
import com.deng.bitpic.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository repository;

    @Test
    public void save() {
        Admin admin = new Admin(KeyUtil.randomUUID(), "15622472033", "password");
        Assert.assertNotNull(repository.save(admin));
    }

    @Test
    public void findByPhone() {
        Admin admin = repository.findByPhone("15622472033");
        Assert.assertEquals("123456", admin.getPassword());
    }
}
package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @Test
    public void save() {
        User user = new User("111", "10010", "hehe", "password", false);
        Assert.assertEquals("hehe", service.save(user).getName());
    }

    @Test
    public void findById() {
        Assert.assertEquals("haha", service.findById("111").getName());
    }

    @Test
    public void deleteById() {
        service.deleteById("111");
    }

    @Test
    public void check() {
        Assert.assertTrue(service.check("10086", "password"));
    }

    @Test
    public void createUserVO() {
        UserVO userVO = service.createUserVO("10010");
        Assert.assertEquals(new BigDecimal(0), userVO.getBalance());
    }
}
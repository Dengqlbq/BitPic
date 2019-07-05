package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.service.AuthorCheckService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorCheckServiceImplTest {

    @Autowired
    private AuthorCheckService service;

    @Test
    public void getWaitingList() {
        List<AuthorCheck> list = service.getWaitingList(PageRequest.of(0, 10));
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void acceptUser() {
        service.acceptUser("12");
    }

    @Test
    public void denyUser() {
        service.denyUser("12", "like");
    }
}
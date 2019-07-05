package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.admin.PicCheck;
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
public class PicCheckServiceImplTest {

    @Autowired
    private PicCheckServiceImpl service;

    @Test
    public void getWaitingList() {
        List<PicCheck> list = service.getWaitingList(PageRequest.of(0, 10));
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void acceptPic() {
        service.acceptPic("a206c57e6b464ddb860bd646d4909064", "fa6977c99b809db68e1c56888ec38bd004719b39");
    }

    @Test
    public void denyPic() {
        service.denyPic("fa6977c99b809db68e1c56888ec38bd004719b39", "haha");
    }
}
package com.deng.bitpic.repository;


import com.deng.bitpic.dataobject.user.Collections;
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
public class CollectionsRepositoryTest {

    @Autowired
    private CollectionsRepository repository;

    @Test
    public void save() {
        Collections collections = new Collections("2", "a206c57e6b464ddb860bd646d4909064", "2", "abce");
        Assert.assertNotNull(repository.save(collections));
    }

    @Test
    public void queryByUserId() {
        Collections c = repository.queryByUserId("123", PageRequest.of(0, 10)).getContent().get(0);
        Assert.assertEquals("234", c.getAuthorId());
    }

    @Test
    public void queryByUserId2() {
        List<Collections> list = repository.queryByUserId("123");
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void deleteByUserId() {
        Assert.assertEquals(new Integer(2),repository.deleteByUserId("1234"));
    }

    @Test
    public void deleteByUserIdAndAndNumber() {
        Assert.assertEquals(new Integer(1), repository.deleteByUserIdAndNumber("123", "number"));
    }
}
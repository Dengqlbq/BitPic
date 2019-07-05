package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster("123", "12", new BigDecimal(123));
        Assert.assertNotNull(repository.save(orderMaster));
    }

    @Test
    public void queryByUserId() {
        List<OrderMaster> oml = repository.queryByUserId("12", PageRequest.of(0, 10)).getContent();
        Assert.assertEquals("123", oml.get(0).getId());
    }

    @Test
    public void queryById() {
        Assert.assertEquals("12", repository.queryById("123").getUserId());
    }

    @Test
    public void queryByUserIdAndStatus() {
        List<OrderMaster> oml = repository.queryByUserIdAndStatus("12", 1, PageRequest.of(0, 10)).getContent();
        Assert.assertEquals("123", oml.get(0).getId());
    }
}
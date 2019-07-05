package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail("123", "23", "34", "45", 0, new BigDecimal(12));
        Assert.assertNotNull(repository.save(orderDetail));
    }

    @Test
    public void queryByMasterId() {
        Assert.assertEquals(1, repository.queryByMasterId("23").size());
    }

    @Test
    public void saveAll() {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail("q", "a", "123", "44", 0, new BigDecimal(10));
        OrderDetail o2 = new OrderDetail("qq", "a", "123", "444", 0, new BigDecimal(10));
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        repository.saveAll(orderDetailList);
    }
}
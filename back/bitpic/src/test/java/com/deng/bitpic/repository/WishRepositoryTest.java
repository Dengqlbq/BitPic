package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.Wish;
import com.deng.bitpic.enums.PicInfoEnum;
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
public class WishRepositoryTest {

    @Autowired
    private WishRepository repository;

    @Test
    public void save() {
        Wish wish = new Wish("122", "12", "23", "we", PicInfoEnum.AUTHORIZATION_STANDARD.getCode(), new BigDecimal(0));
        Assert.assertEquals("12", repository.save(wish).getUserId());
    }

    @Test
    public void queryByUserIdAndAndNumber() {
        Assert.assertEquals("12", repository.queryByUserIdAndAndNumber("12", "we").getId());
    }

    @Test
    public void queryByUserId() {
        Assert.assertEquals(2, repository.queryByUserId("12", PageRequest.of(0, 10)).getContent().size());
    }

    @Test
    public void deleteByUserIdAndAndNumber() {
        Assert.assertEquals(new Integer(1), repository.deleteByUserIdAndNumber("1", "1"));
    }

    @Test
    public void deleteByUserId() {
        Assert.assertEquals(new Integer(2), repository.deleteByUserId("12"));
    }

    @Test
    public void deleteAll() {
        List<Wish> wishList = repository.queryByUserId("12", PageRequest.of(0, 10)).getContent();
        Assert.assertEquals(2, wishList.size());
        repository.deleteAll(wishList);
    }

    @Test
    public void countByUserId() {
        Assert.assertEquals(new Integer(2), repository.countByUserId("12"));
    }
}
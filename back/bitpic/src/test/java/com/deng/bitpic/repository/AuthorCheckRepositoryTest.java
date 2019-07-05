package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.enums.CheckEnum;
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
public class AuthorCheckRepositoryTest {

    @Autowired
    private AuthorCheckRepository repository;

    @Test
    public void save() {
        AuthorCheck authorCheck = new AuthorCheck("12432");
        Assert.assertNotNull(repository.save(authorCheck));
    }

    @Test
    public void queryByUserId() { Assert.assertEquals("test", repository.queryByUserId("12").getReason());
    }

    @Test
    public void queryByAcceptOrderByCreateTimeAsc() {
        List<AuthorCheck> list = repository.queryByStatusOrderByCreateTimeAsc(CheckEnum.STATUS_WAIT.getCode(), PageRequest.of(0, 10)).getContent();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void deleteByUserId() {
        Assert.assertEquals(new Integer(0), repository.deleteByUserId("12432"));
    }
}
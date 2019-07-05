package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.enums.CheckEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PicCheckRepositoryTest {

    @Autowired
    private PicCheckRepository repository;

    @Test
    public void save() {
        PicCheck picCheck = new PicCheck("abcd", "5");
        picCheck.setFormats(new String[]{"jpg", "png"});
        picCheck.setAuthFormats(new String[]{"jpeg", "jpg"});
        Assert.assertNotNull(repository.save(picCheck));
    }

    @Test
    public void queryByAcceptOrderByCreateTimeAsc() {
        List<PicCheck> list = repository.queryByStatusOrderByCreateTimeAsc(CheckEnum.STATUS_WAIT.getCode(), PageRequest.of(0, 10)).getContent();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByNumber() {
        PicCheck picCheck = repository.queryByNumber("1223");
        Assert.assertEquals("nu2m3", picCheck.getUserId());
    }

    @Test
    public void queryByUserIdAndStatusOrderByCreateTimeDesc() {
        Page<PicCheck> page = repository.queryByUserIdAndStatusOrderByCreateTimeDesc("nu2m3", CheckEnum.STATUS_WAIT.getCode(), PageRequest.of(0, 10));
        Assert.assertEquals(2, page.getContent().size());
    }
}

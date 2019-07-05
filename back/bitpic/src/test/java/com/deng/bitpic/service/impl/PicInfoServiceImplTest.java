package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.service.PicInfoService;
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
public class PicInfoServiceImplTest {

    @Autowired
    private PicInfoService service;

    @Test
    public void queryPicInfoByKeywordsMatches() {
        Assert.assertEquals(3, service.queryByKeywordsMatches("海", PageRequest.of(0, 10)).size());
    }

    @Test
    public void queryByNumber() {
        Assert.assertEquals("1", service.queryByNumber("abcd").getAuthorId());
    }

    @Test
    public void deleteByNumber() {
        Assert.assertEquals(new Long(1), new Long(service.deleteByNumber("abcde")));
    }

    @Test
    public void save() {
        PicInfo picInfo = new PicInfo("abcde", "4", "haha", 0, 0, new String[]{"PNG"}, new Integer[]{0}, new String[]{"海贼"}, false);
        Assert.assertEquals("4", service.save(picInfo).getAuthorId());
    }

    @Test
    public void filter() {
        List<PicInfo> picInfoList = service.queryByKeywordsMatches("海", PageRequest.of(0, 10));
        List<PicInfo> pl = service.filter(picInfoList, "PNG", null, null, false);
        Assert.assertEquals(1, pl.size());
    }
}
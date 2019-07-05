package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.enums.CheckEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PicInfoRepositoryTest {

    @Autowired
    private PicInfoRepository repository;

    @Test
    public void save() {
        PicInfo picInfo = new PicInfo("abcd", "1", "haha", 11, 12, new String[]{"JPG", "PNG"}, new Integer[]{0, 1}, new String[]{"海贼", "航海"}, false);
        Integer[][] s1 = new Integer[2][2];
        s1[0][0] = 0;
        s1[0][1] = 1;
        s1[1][0] = 0;
        s1[1][1] = 1;
        Float[][] s2 = new Float[2][2];
        s2[0][0] = 0f;
        s2[0][1] = 1f;
        s2[1][0] = 0f;
        s2[1][1] = 1f;

        picInfo.setSizePixel(s1);
        picInfo.setSizeCentimetre(s2);
        picInfo.setPricePlus(new BigDecimal(10));
        picInfo.setStatus(0);
        repository.save(picInfo);
    }

    @Test
    public void queryByNumber() {
        PicInfo picInfo = repository.queryByNumber("abcd");
        Assert.assertNull(picInfo);
    }

    @Test
    public void queryByAuthorId() {
        List<PicInfo> list = repository.queryByAuthorIdAndCheckStatus("1", CheckEnum.STATUS_ACCEPT.getCode(),PageRequest.of(0, 10)).getContent();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void deleteByNumber() {
        Assert.assertEquals(new Long(1), new Long(repository.deleteByNumber("asefsdf")));
    }

    @Test
    public void queryPicInfoByKeywordsMatches() {
        Page<PicInfo> picInfoPage = repository.queryPicInfoByKeywordsMatchesAndCheckStatus("海贼", CheckEnum.STATUS_ACCEPT.getCode(), PageRequest.of(0, 10));
        Assert.assertEquals(3, picInfoPage.getContent().size());
    }
}
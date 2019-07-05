package com.deng.bitpic.utils;

import com.deng.bitpic.dataobject.PicInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PicInfoUtilTest {

    @Test
    public void fillingFromPath() {
        PicInfo picInfo = new PicInfo();
        String path = "/Volumes/H/Java/store/1/ab/";
        picInfo.setAuthorId("aut");
        PicInfoUtil.fillingFromPath(picInfo, path);
        picInfo.setAuthorName("name");
    }
}
package com.deng.bitpic.utils;

import com.deng.bitpic.constant.ThumbnailsConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.DecimalFormat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThumbnailsUtilTest {

    @Test
    public void thumFolder() {
        String src = "/Volumes/H/Java/store/1/ab/";
        String des = "/Volumes/H/Java/store/1/abc/";
        try {
            ThumbnailsUtil.thumFolder(src, des, ThumbnailsConstant.BIG_SCALE, true);
        } catch (IOException e) {
        }
    }
}
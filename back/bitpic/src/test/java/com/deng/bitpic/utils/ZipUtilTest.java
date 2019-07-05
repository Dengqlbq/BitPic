package com.deng.bitpic.utils;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ZipUtilTest {

    @Test
    public void zipFolder() {
        String src = "/Volumes/H/Java/store/2";
        String des = "/Volumes/H/Java/store/2/0.zip";
        String pwd = "123";
        try {
            ZipUtil.zipFolder(src, des, pwd);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }
}
package com.deng.bitpic.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class IpfsUtilTest {

    @Test
    public void upload() {
        String filePathName = "/Volumes/H/Java/store/1/0big.png";
        try {
            String hash = IpfsUtil.upload(filePathName);
            log.info(hash);
        } catch (IOException e) {
            log.error("error");
        }
    }

    @Test
    public void download() {
        String filePathName = "/Volumes/H/Java/store/1/2.png";
        try {
            IpfsUtil.download("QmVDqdrjPPVX6imfuWU2pCA2UynkYjqW7GgJftSp6NXtQu", filePathName);
            log.info(filePathName);
        } catch (IOException e) {
            log.error("error");
        }
    }
}
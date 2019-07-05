package com.deng.bitpic.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AESUtilTest {

    @Test
    public void encrypt() {
        String key = "1234567890123456";
        String src = "haha";
        try {
            log.info(AESUtil.encrypt(src, key));
        } catch (Exception e) {

        }

    }

    @Test
    public void decrypt() {
        String key = "1234567890123456";
        String src = "Y70Fwf1npqcmSpAVYtjUFg8P+y5Ti9vrsPqvG9Rk+/i9NVXV97JCr5NpbWZc5hsL";
        try {
            log.info(AESUtil.decrypt(src, key));
        } catch (Exception e) {

        }

    }
}
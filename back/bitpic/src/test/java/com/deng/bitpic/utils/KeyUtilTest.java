package com.deng.bitpic.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class KeyUtilTest {

    @Test
    public void randomUUID() {
      log.info(KeyUtil.randomUUID());
    }

    @Test
    public void getKey() {
        log.info(KeyUtil.getKey("haha"));
    }
}
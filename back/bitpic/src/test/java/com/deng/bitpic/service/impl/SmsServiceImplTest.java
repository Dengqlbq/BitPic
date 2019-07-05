package com.deng.bitpic.service.impl;

import com.deng.bitpic.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SmsServiceImplTest {

    @Autowired
    private SmsService service;

    @Test
    public void sendCode() {
        String number = "15622472033";
        service.sendCode(number, 0);
    }

    @Test
    public void createCode() {
        log.info(service.createCode());
    }
}
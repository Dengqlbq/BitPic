package com.deng.bitpic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisRepositoryTest {

    @Autowired
    private RedisRepository repository;

    @Test
    public void set() {
        repository.set("token", "value", 1000, TimeUnit.SECONDS);
    }

    @Test
    public void delete() {
        repository.delete("token");
    }
}
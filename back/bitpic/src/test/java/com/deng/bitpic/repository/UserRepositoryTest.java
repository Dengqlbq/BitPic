package com.deng.bitpic.repository;

import com.deng.bitpic.dataobject.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void save() {
        User user = new User("2222", "10086", "haha", "password", false);
        Assert.assertNotNull(repository.save(user));
    }

    @Test
    public void findById() {
        User user = repository.findById("2222").get();
        Assert.assertEquals("haha", user.getName());
    }

    @Test
    public void findByPhone() {
        Assert.assertEquals("haha", repository.findByPhone("10086").getName());
    }

    @Test
    public void delete() {
        repository.deleteById("2222");
    }
}
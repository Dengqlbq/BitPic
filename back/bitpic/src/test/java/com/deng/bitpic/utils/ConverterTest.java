package com.deng.bitpic.utils;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConverterTest {

    @Autowired
    private UserService userService;

    @Test
    public void user2UserVO() {
        User user = userService.findByPhone("15622472033");
        UserVO userVO = Converter.user2UserVO(user);
        userVO.setWishCount(5);
    }
}
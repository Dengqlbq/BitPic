package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.repository.UserRepository;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.service.WishService;
import com.deng.bitpic.utils.Converter;
import com.deng.bitpic.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description: 用户（包括摄影师）
 * @author: Deng
 * @create: 2019-01-17
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private WishService wishService;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(String id) {
        User user = repository.findById(id).get();
        return user.getId() != null ? user : null;
    }

    @Override
    public User findByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void incrBalance(String id, BigDecimal amount) {
        // TODO 异常
        User user = findById(id);
        if (user != null && amount.intValue() > 0) {
            BigDecimal balance = user.getBalance();
            user.setBalance(balance.add(amount));
            save(user);
        }
    }

    @Override
    public void decrBalance(String id, BigDecimal amount) {
        User user = findById(id);
        if (user != null && amount.compareTo(new BigDecimal(0)) >= 0) {
            BigDecimal balance = user.getBalance();
            if (balance.compareTo(amount) >= 0) {
                user.setBalance(balance.subtract(amount));
                save(user);
            } else {
                throw new BitPicException(ResultEnum.BALANCE_INSUFFICIENT);
            }
        }
    }

    @Override
    public Boolean check(String phone, String password) {
        User user = repository.findByPhone(phone);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public UserVO createUserVO(String phone) {
        User user = findByPhone(phone);
        UserVO userVO = Converter.user2UserVO(user);
        Integer wishCount = wishService.countByUserId(user.getId());
        userVO.setWishCount(wishCount);

        return userVO;
    }
}

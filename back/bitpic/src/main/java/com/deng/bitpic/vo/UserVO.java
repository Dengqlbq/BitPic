package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 用户信息
 * @author: Deng
 * @create: 2019-02-17
 */
@Data
public class UserVO {

    /** 用户ID */
    private String id;

    /** 用户名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 是否摄影师 */
    private Boolean photographer;

    /** 是否已认证摄影师 */
    private Boolean certificated;

    /** 是否拥有头像 */
    private Boolean haveAvatar;

    /** 心愿单作品数量 */
    private Integer wishCount;

    /** 余额 */
    private BigDecimal balance;

    public UserVO(String id, String name, String phone, Boolean photographer) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photographer = photographer;
        this.certificated = false;
        this.haveAvatar = false;
        this.wishCount = 0;
        this.balance = new BigDecimal(0);
    }

    public UserVO() {
    }
}

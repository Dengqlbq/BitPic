package com.deng.bitpic.dataobject.user;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 用户（包括摄影师）
 * @author: Deng
 * @create: 2019-01-17
 */
@Data
@Entity
@DynamicUpdate
public class User {

    /** 用户ID */
    @Id
    private String id;

    /** 用户昵称 */
    private String name;

    /** 密码 */
    private String password;

    /** 手机 */
    private String phone;

    /** 是否摄影师 */
    private Boolean photographer;

    /** 是否已认证摄影师 */
    private Boolean certificated;

    /** 是否拥有头像 */
    private Boolean haveAvatar = false;

    /** 余额 */
    private BigDecimal balance = new BigDecimal(0);

    /** 创建日期 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public User(String id, String phone, String name, String password, Boolean photographer) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.photographer = photographer;
    }

    public User() {
    }
}

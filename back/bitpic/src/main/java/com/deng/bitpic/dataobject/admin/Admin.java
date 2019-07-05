package com.deng.bitpic.dataobject.admin;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @description: 管理员
 * @author: Deng
 * @create: 2019-03-01
 */
@Data
@Entity
@DynamicUpdate
public class Admin {

    /** 管理员ID */
    @Id
    private String id;

    /** 管理员手机号 */
    private String phone;

    /** 密码 */
    private String password;

    /** 创建日期 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Admin(String id, String phone, String password) {
        this.id = id;
        this.phone = phone;
        this.password = password;
    }

    public Admin() {
    }
}

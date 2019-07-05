package com.deng.bitpic.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-27
 */
@Data
public class AuthorCheckVO {

    /** 审核状态 */
    private Integer status;

    /** 审核不通过原因 */
    private String reason;

    /** 创建日期 */
    private Date createTime;

    public AuthorCheckVO(Integer status, String reason, Date createTime) {
        this.status = status;
        this.reason = reason;
        this.createTime = createTime;
    }
}

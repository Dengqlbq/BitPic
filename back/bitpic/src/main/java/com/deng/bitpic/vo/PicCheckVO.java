package com.deng.bitpic.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-27
 */
@Data
public class PicCheckVO {

    /** 作品编号 */
    private String number;

    /** 审核状态 */
    private Integer status;

    /** 审核失败原因 */
    private String reason;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public PicCheckVO() {
    }
}

package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2019-03-05
 */
@Data
public class OrderDetailVO {

    /** 作者ID */
    private String authorId;

    /** 作品编号 */
    private String number;

    /** 授权类型 */
    private Integer type;

    /** 价格 */
    private BigDecimal price;

    public OrderDetailVO() {
    }
}

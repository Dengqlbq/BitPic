package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-25
 */
@Data
public class WishVO {

    /** 作者ID */
    private String authorId;

    /** 作品编号 */
    private String number;

    /** 授权类型 */
    private Integer type;

    /** 价格 */
    private BigDecimal price;

    public WishVO(String authorId, String number, Integer type, BigDecimal price) {
        this.authorId = authorId;
        this.number = number;
        this.type = type;
        this.price = price;
    }
}

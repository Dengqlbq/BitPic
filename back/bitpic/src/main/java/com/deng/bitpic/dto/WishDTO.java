package com.deng.bitpic.dto;

import lombok.Data;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-27
 */
@Data
public class WishDTO {

    /** 作品编号 */
    private String number;

    /** 授权类型 */
    private Integer type;
}

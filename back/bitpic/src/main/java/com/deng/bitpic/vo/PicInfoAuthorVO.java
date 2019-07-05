package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 作品信息（作者）
 * @author: Deng
 * @create: 2019-02-28
 */
@Data
public class PicInfoAuthorVO {

    /** 作品编号 */
    private String number;

    /** 点赞 */
    private Integer like;

    /** 收藏 */
    private Integer collection;

    /** 关键词 */
    private String[] keywords;

    /** 标准授权价格 */
    private BigDecimal priceStandard;

    /** 扩展授权价格 */
    private BigDecimal pricePlus;

    /** 标准授权出售份数 */
    private Integer saleStandard;

    /** 扩展授权出售分数 */
    private Integer salePlus;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    public PicInfoAuthorVO() {
    }
}

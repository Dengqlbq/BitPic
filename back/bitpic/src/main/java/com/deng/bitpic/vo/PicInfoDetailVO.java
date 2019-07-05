package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 作品信息（详情）
 * @author: Deng
 * @create: 2019-01-26
 */
@Data
public class PicInfoDetailVO {

    /** 作品编号 */
    private String number;

    /** 摄影师ID */
    private String authorId;

    /** 摄影师昵称 */
    private String authorName;

    /** 是否套图 */
    private Boolean group;

    /** 略缩图格式 */
    private String[] formats;

    /** 略缩图方向 */
    private Integer[] directions;

    /** 长宽（像素) */
    private Integer[][] sizePixel;

    /** 长宽 (厘米) */
    private Float[][] sizeCentimetre;

    /** DPI */
    private Integer[] dpi;

    /** 标准授权价格 */
    private BigDecimal priceStandard;

    /** 扩展授权价格 */
    private BigDecimal pricePlus;

    /** 是否下架, 用于用户收藏到详情的跳转 */
    private Integer status;

    public PicInfoDetailVO() {
    }
}

package com.deng.bitpic.dataobject;

import com.deng.bitpic.enums.CheckEnum;
import com.deng.bitpic.enums.PicInfoEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 作品信息
 * @author: Deng
 * @create: 2019-01-07
 */
@Data
@Document(indexName = "bitpic_picinfo", type = "PicInfo", shards = 1,replicas = 0, refreshInterval = "-1")
public class PicInfo {

    /** 作品编号 */
    @Id
    private String number;

    /** 摄影师ID */
    @Field(type = FieldType.Keyword)
    private String authorId;

    /** 摄影师昵称 */
    @Field(type = FieldType.Keyword)
    private String authorName;

    /** 点赞 */
    @Field(type = FieldType.Integer)
    private Integer like = 0;

    /** 收藏 */
    @Field(type = FieldType.Integer)
    private Integer collection = 0;

    /** 是否套图 */
    @Field(type = FieldType.Boolean)
    private Boolean group;

    /** 略缩图格式 */
    @Field(type = FieldType.Keyword)
    private String[] formats;

    /** 略缩图方向 */
    @Field(type = FieldType.Long)
    private Integer[] directions;

    /** 长宽（像素) */
    @Field(type = FieldType.Long)
    private Integer[][] sizePixel;

    /** 长宽 (厘米) */
    @Field(type = FieldType.Float)
    private Float[][] sizeCentimetre;

    /** DPI */
    @Field(type = FieldType.Long)
    private Integer[] dpi;

    /** 关键词 */
    @Field(type = FieldType.Keyword)
    private String[] keywords;

    /** 标准授权价格 */
    @Field(type = FieldType.Double)
    private BigDecimal priceStandard;

    /** 扩展授权价格 */
    @Field(type = FieldType.Double)
    private BigDecimal pricePlus;

    /** 标准授权出售份数 */
    @Field(type = FieldType.Integer)
    private Integer saleStandard = 0;

    /** 扩展授权出售份数 */
    @Field(type = FieldType.Integer)
    private Integer salePlus = 0;

    /** IPFS地址 */
    @Field(type = FieldType.Keyword)
    private String ipfs;

    /** 商品状态 */
    @Field(type = FieldType.Integer)
    private Integer status = PicInfoEnum.STATUS_DOWN.getCode();

    /** 审核状态 */
    @Field(type = FieldType.Integer)
    private Integer checkStatus = CheckEnum.STATUS_WAIT.getCode();

    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime = new Date();

    /** 更新时间 */
    @Field(type = FieldType.Date)
    private Date updateTime;

    public PicInfo(String number, String authorId, String authorName, Integer like, Integer collection, String[] formats,
                   Integer[] directions, String[] keywords, Boolean group) {
        this.number = number;
        this.authorId = authorId;
        this.authorName = authorName;
        this.like = like;
        this.collection = collection;
        this.formats = formats;
        this.directions = directions;
        this.keywords = keywords;
        this.group = group;
    }

    public PicInfo() {
    }
}

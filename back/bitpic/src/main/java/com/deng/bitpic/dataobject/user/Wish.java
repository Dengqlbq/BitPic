package com.deng.bitpic.dataobject.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * @description: 用户心愿（购物车）
 * @author: Deng
 * @create: 2019-01-23
 */
@Data
@Document(indexName = "bitpic_wish", type = "Wish", shards = 1,replicas = 0, refreshInterval = "-1")
public class Wish {

    /** ID */
    @Id
    private String id;

    /** 用户ID */
    @Field(type = FieldType.Keyword)
    private String userId;

    /** 摄影师ID */
    @Field(type = FieldType.Keyword)
    private String authorId;

    /** 作品编号 */
    @Field(type = FieldType.Keyword)
    private String number;

    /** 授权类型 */
    @Field(type = FieldType.Integer)
    private Integer type;

    /** 价格 */
    @Field(type = FieldType.Double)
    private BigDecimal price;

    public Wish(String id, String userId, String authorId, String number, Integer type, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.authorId = authorId;
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public Wish() {
    }
}

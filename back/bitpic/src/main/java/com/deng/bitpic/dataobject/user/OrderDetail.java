package com.deng.bitpic.dataobject.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单详情
 * @author: Deng
 * @create: 2019-01-27
 */
@Data
@Document(indexName = "bitpic_orderdetail", type = "OrderDetail", shards = 1,replicas = 0, refreshInterval = "-1")
public class OrderDetail {

    /** ID */
    @Id
    private String id;

    /** 主表ID */
    @Field(type = FieldType.Keyword)
    private String masterId;

    /** 作者ID */
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

    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime;

    public OrderDetail(String id, String masterId, String authorId, String number, Integer type, BigDecimal price) {
        this.id = id;
        this.masterId = masterId;
        this.authorId = authorId;
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public OrderDetail() {
    }
}

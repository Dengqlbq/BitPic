package com.deng.bitpic.dataobject.user;

import com.deng.bitpic.enums.OrderEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2019-01-27
 */
@Data
@Document(indexName = "bitpic_ordermaster", type = "OrderMaster", shards = 1,replicas = 0, refreshInterval = "-1")
public class OrderMaster {

    /** ID */
    @Id
    private String id;

    /** 用户ID */
    @Field(type = FieldType.Keyword)
    private String userId;

    /** 总价 */
    @Field(type = FieldType.Double)
    private BigDecimal total;

    /** 订单状态 */
    @Field(type = FieldType.Integer)
    private Integer status = OrderEnum.STATUS_UNPAID.getCode();

    /** 是否下载过，用于订单退款 */
    @Field(type = FieldType.Boolean)
    private Boolean hadDownload = false;

    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime;

    /** 更新时间 */
    @Field(type = FieldType.Date)
    private Date updateTime;

    public OrderMaster(String id, String userId, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.total = total;
    }

    public OrderMaster(String id, String userId, BigDecimal total, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderMaster() {
    }
}

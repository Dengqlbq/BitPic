package com.deng.bitpic.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单主表
 * @author: Deng
 * @create: 2019-03-05
 */
@Data
public class OrderMasterVO {

    /** ID */
    private String id;

    /** 总价 */
    private BigDecimal total;

    /** 订单状态 */
    private Integer status;

    /** 是否下载过 */
    private Boolean hadDownload;

    /** 订单详情列表 */
    private List<OrderDetailVO> orderDetails;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public OrderMasterVO() {
    }
}

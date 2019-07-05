package com.deng.bitpic.dataobject.admin;

import com.deng.bitpic.enums.CheckEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @description: 摄影师认证
 * @author: Deng
 * @create: 2019-02-08
 */
@Data
@Document(indexName = "bitpic_authorcheck", type = "AuthorCheck", shards = 1,replicas = 0, refreshInterval = "-1")
public class AuthorCheck {

    /** 用户ID */
    @Id
    private String userId;

    /** 审核不通过原因 */
    @Field(type = FieldType.Text)
    private String reason;

    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime = new Date();

    /** 修改时间 */
    @Field(type = FieldType.Date)
    private Date updateTime;

    /** 认证状态 */
    @Field(type = FieldType.Integer)
    private Integer status = CheckEnum.STATUS_WAIT.getCode();

    public AuthorCheck(String userId) {
        this.userId = userId;
    }

    public AuthorCheck() {
    }
}

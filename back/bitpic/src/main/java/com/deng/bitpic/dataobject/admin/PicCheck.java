package com.deng.bitpic.dataobject.admin;

import com.deng.bitpic.enums.CheckEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-10
 */
@Data
@Document(indexName = "bitpic_piccheck", type = "PicCheck", shards = 1,replicas = 0, refreshInterval = "-1")
public class PicCheck {

    /** 作品编号 */
    @Id
    private String number;

    /** 用户ID */
    @Field(type = FieldType.Keyword)
    private String userId;

    /** 图片格式 */
    @Field(type = FieldType.Keyword)
    private String[] formats;

    /** 版权证明图片材料格式 */
    @Field(type = FieldType.Keyword)
    private String[] authFormats;

    /** 审核状态 */
    @Field(type = FieldType.Integer)
    private Integer status = CheckEnum.STATUS_WAIT.getCode();

    /** 审核失败原因 */
    @Field(type = FieldType.Text)
    private String reason;

    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createTime = new Date();

    /** 修改时间 */
    @Field(type = FieldType.Date)
    private Date updateTime;

    public PicCheck(String number, String userId) {
        this.number = number;
        this.userId = userId;
    }

    public PicCheck(String number, String userId, String[] formats) {
        this.number = number;
        this.userId = userId;
        this.formats = formats;
    }

    public PicCheck() {
    }
}

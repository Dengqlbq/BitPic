package com.deng.bitpic.dataobject.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-23
 */
@Data
@Document(indexName = "bitpic_collections", type = "Collections", shards = 1,replicas = 0, refreshInterval = "-1")
public class Collections {

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

    public Collections(String id, String userId, String authorId, String number) {
        this.id = id;
        this.userId = userId;
        this.authorId = authorId;
        this.number = number;
    }

    public Collections() {
    }
}

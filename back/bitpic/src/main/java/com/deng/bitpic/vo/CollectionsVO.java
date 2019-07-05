package com.deng.bitpic.vo;

import lombok.Data;

/**
 * @description: 用户收藏
 * @author: Deng
 * @create: 2019-01-25
 */
@Data
public class CollectionsVO {

    /** 摄影师ID */
    private String authorId;

    /** 作品编号 */
    private String number;

    public CollectionsVO(String authorId, String number) {
        this.authorId = authorId;
        this.number = number;
    }
}

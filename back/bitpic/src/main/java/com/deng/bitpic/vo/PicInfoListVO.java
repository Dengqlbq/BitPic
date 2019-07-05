package com.deng.bitpic.vo;

import lombok.Data;

/**
 * @description: 作品信息（搜索）
 * @author: Deng
 * @create: 2019-01-26
 */
@Data
public class PicInfoListVO {

    /** 作品编号 */
    private String number;

    /** 摄影师ID */
    private String authorId;

    /** 摄影师昵称 */
    private String authorName;

    /** 点赞 */
    private Integer like;

    /** 收藏 */
    private Integer collection;

    /** 宽高（像素） 避免前端加载图片抖动 */
    private Integer[] size;

    public PicInfoListVO(String number, String authorId, Integer like, Integer collection) {
        this.number = number;
        this.authorId = authorId;
        this.like = like;
        this.collection = collection;
    }

    public PicInfoListVO(String number, String authorId, Integer like, Integer collection, Integer[] size) {
        this.number = number;
        this.authorId = authorId;
        this.like = like;
        this.collection = collection;
        this.size = size;
    }

    public PicInfoListVO() {
    }
}

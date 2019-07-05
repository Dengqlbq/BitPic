package com.deng.bitpic.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: 作品信息（创建）
 * @author: Deng
 * @create: 2019-02-12
 */
@Data
public class PicInfoCreateForm {

    /** 用于产生作品编号的种子 */
    @NotBlank
    private String seed;

    /** 用户ID */
    @NotBlank
    private String userId;

    /** 关键词 + 类别 */
    @NotBlank
    private String keywords;

    /** 标准授权价格 */
    @NotNull
    @Min(0)
    private BigDecimal priceStandard;

    /** 扩展授权价格 */
    @NotNull
    @Min(0)
    private BigDecimal pricePlus;
}

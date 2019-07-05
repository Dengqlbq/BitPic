package com.deng.bitpic.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: 作品信息（更新）
 * @author: Deng
 * @create: 2019-03-01
 */
@Data
public class PicInfoUpdateForm {

    /** 用户ID */
    @NotBlank
    private String userId;

    /** 作品编号 */
    @NotBlank
    private String number;

    /** 标准授权价格 */
    @NotNull
    @Min(0)
    private BigDecimal priceStandard;

    /** 扩展授权价格 */
    @NotNull
    @Min(0)
    private BigDecimal pricePlus;

    /** 关键词 + 类别 */
    @NotBlank
    private String keywords;

    /** 状态（上架or下架）*/
    @NotNull
    private Integer status;
}

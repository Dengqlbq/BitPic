package com.deng.bitpic.exception;

import com.deng.bitpic.enums.ResultEnum;
import lombok.Data;

/**
 * @description: 业务逻辑统一异常
 * @author: Deng
 * @create: 2019-01-24
 */
@Data
public class BitPicException extends RuntimeException {

    private Integer code;

    public BitPicException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BitPicException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}

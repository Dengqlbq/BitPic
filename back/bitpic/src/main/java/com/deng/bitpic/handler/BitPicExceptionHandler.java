package com.deng.bitpic.handler;

import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 业务逻辑同一异常处理器
 * @author: Deng
 * @create: 2019-01-24
 */
@ControllerAdvice
public class BitPicExceptionHandler {

    @ExceptionHandler(value = BitPicException.class)
    @ResponseBody
    public ResultVO handle(BitPicException e) {
        return ResultVO.of(e.getMessage(), e.getCode());
    }
}

package com.deng.bitpic.vo;

import com.deng.bitpic.enums.ResultEnum;
import lombok.Data;

/**
 * @description: Http请求响应最外层对象
 * @author: Deng
 * @create: 2019-01-17
 */
@Data
public class ResultVO<T> {

    /** 响应消息 */
    private String message;

    /** 响应码 */
    private Integer code;

    /** 响应数据 */
    private T data;

    public ResultVO(String message, Integer code, T t) {
        this.message = message;
        this.code = code;
        this.data = t;
    }

    public ResultVO(String message, Integer code) {

    }

    public static ResultVO of(ResultEnum resultEnum, Object o) {
        return new ResultVO(resultEnum.getMessage(), resultEnum.getCode(), o);
    }

    public static ResultVO of(ResultEnum resultEnum) {
        return ResultVO.of(resultEnum, null);
    }

    public static ResultVO of(String message, Integer code) {
        return new ResultVO(message, code, null);
    }

    public ResultVO() {
    }
}

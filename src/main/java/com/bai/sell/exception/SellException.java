package com.bai.sell.exception;

import com.bai.sell.enums.ResultEnum;

/**
 * 全局异常
 */
public class SellException extends RuntimeException {

    private  Integer code;

    /** 重写构造方法 */
    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

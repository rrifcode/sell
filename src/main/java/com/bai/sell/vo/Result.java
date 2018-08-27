package com.bai.sell.vo;


import lombok.Data;

/**
 * http请求返回的最外层的对象
 * @author Administrator
 */
@Data
public class Result<T> {

    /** 错误码 */
    private Integer code;

    /** 错误信息 */
    private String msg;

    /** 具体内容 */
    private T data;


}

package com.bai.sell.enums;


import lombok.Getter;

/**
 * 商品状态
 * @author Administrator
 */

@Getter
public enum ProductStatusEnum {

    UP(0, "在售"),
    DOWN(1, "下架");

    private Integer code;
    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

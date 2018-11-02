package com.bai.sell.enums;

import lombok.Getter;

/**
 *
 * @author Administrator
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_STATUS_UPDATE_FAIL(15, "订单状态更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单里没有商品"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    ORDER_PAY_STATUS_UPDATE_FAIL(18, "订单支付状态更新失败"),

    PARAM_ERROR(1, "参数不正确"),

    CART_EMPTY_ERROR(18, "购物车为空"),

    INSERT_DB_ERROR(20, "插入数据库失败"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}

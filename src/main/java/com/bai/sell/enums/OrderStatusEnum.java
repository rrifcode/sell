package com.bai.sell.enums;


import lombok.Getter;

/**
 * 订单状态
 * 枚举类不能使用@Data注解,需要手动生成构造方法
 * @author Administrator
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新下单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

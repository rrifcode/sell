package com.bai.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 入参较多的时候,把入参封装成一个对象
 * Created by Bai
 * 2018-10-26 16:20
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "联系电话不能为空")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址不能为空")
    private String address;

    /**
     * 买家微信openid
     */
    @NotEmpty(message = "openid不能为空")
    private String openid;

    /**
     * 买家购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

}

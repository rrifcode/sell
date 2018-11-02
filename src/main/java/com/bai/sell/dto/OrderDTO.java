package com.bai.sell.dto;


import com.bai.sell.entity.OrderDetail;
import com.bai.sell.enums.OrderStatusEnum;
import com.bai.sell.enums.PayStatusEnum;
import com.bai.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象
 * 订单
 */
@Data
//此注解表示当返回结果中某个属性返回的是null时,不返回此字段. 旧用法
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//新用法. 如果有许多个对象文件,全部使用, 在配置文件里配置.
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id */
    private String orderId;

    /** 买家名字 */
    private String orderBuyerName;

    /** 买家电话 */
    private String orderBuyerPhone;

    /** 买家地址 */
    private String orderBuyerAddress;

    /** 买家openid */
    private String  orderBuyerOpenid;

    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单 */
    private Integer orderStatus;

    /** 订单拍支付状态, 默认为0未支付 */
    private Integer orderPayStatus;

    /** 订单创建时间 . 此注解表示把Date类型的时间13位,转成10位long型时间戳*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date orderCreateTime;

    /** 订单更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date orderUpdateTime;

    /** 如协议规定字段为空,也要返回这个list值,赋初始值 */
    /** 如List 显示[] , String 显示 ""  */
    //String test = "";
    //private List<OrderDetail> orderDetailList = new ArrayList<>();
    private List<OrderDetail> orderDetailList;

}

package com.bai.sell.dto;


import com.bai.sell.entity.OrderDetail;
import com.bai.sell.enums.OrderStatusEnum;
import com.bai.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象
 * 订单
 */
@Data
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

    /** 订单创建时间 */
    private Date orderCreateTime;

    /** 订单更新时间 */
    private Date orderUpdateTime;

    private List<OrderDetail> orderDetailList;

}

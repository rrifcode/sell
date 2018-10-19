package com.bai.sell.entity;


import com.bai.sell.enums.OrderStatusEnum;
import com.bai.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
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
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();

    /** 订单拍支付状态, 默认为0未支付 */
    private Integer orderPayStatus= PayStatusEnum.WAIT.getCode();

    /** 订单创建时间 */
    private Date orderCreateTime;

    /** 订单更新时间 */
    private Date orderUpdateTime;

}

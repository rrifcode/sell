package com.bai.sell.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    @Id
    private  String detailId;

    /** 订单id */
    private  String orderId;

    /** 商品id */
    private  String productId;

    /** 商品名字 */
    private  String productName;

    /** 商品单价 */
    private  BigDecimal productPrice;

    /** 商品数量 */
    private  Integer productQuantity;

    /** 商品图片 */
    private  String productIcon;

    /** 订单创建时间 */
    private  String orderCreateTime;

    /** 订单更新时间 */
    private  String orderUpdateTime;


}

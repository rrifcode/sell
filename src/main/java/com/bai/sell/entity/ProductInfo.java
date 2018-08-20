package com.bai.sell.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品实体类
 * @author Administrator
 */

@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    /** 商品id 字符串类型，不用@GeneratedValue注解自增*/
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品小图 */
    private String productIcon;

    /** 商品状态 0正常 1下架 */
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;

   /* public ProductInfo() {
    }

    public ProductInfo(String productName, BigDecimal productPrice, Integer productStock, String productDescription,
                       String productIcon, Integer productStatus, Integer categoryType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }*/
}


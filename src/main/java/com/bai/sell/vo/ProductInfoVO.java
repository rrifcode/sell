package com.bai.sell.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息VO类，一些参数不应暴露给外界
 *
 * @author Administrator
 */
@Data
public class ProductInfoVO {

    /** 此注解使用在属性上，把属性名序列化成另一个名称：productId序列化为id */
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品小图地址 */
    @JsonProperty("icon")
    private String productIcon;

}

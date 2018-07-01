package com.bai.sell.service;


import com.bai.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 * @author Administrator
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查找所有正在售卖商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查找所有商品
     * 要分页  springframework
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);


}

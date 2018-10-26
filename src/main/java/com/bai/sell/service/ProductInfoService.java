package com.bai.sell.service;


import com.bai.sell.dto.CartDTO;
import com.bai.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 * @author Administrator
 */
public interface ProductInfoService {

    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查找所有正在售卖商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     *  查找所有商品
     *  要分页  springframework
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 添加商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 增加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);


}

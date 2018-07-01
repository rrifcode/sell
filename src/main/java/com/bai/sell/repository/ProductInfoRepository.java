package com.bai.sell.repository;


import com.bai.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Dao层接口
 * @author Administrator
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{  /** 主键是String类型 */

    /** 根据商品状态查找商品 */
    List<ProductInfo> findByProductStatus(Integer productStatus);

}


package com.bai.sell.service;


import com.bai.sell.entity.ProductCategory;

import java.util.List;

/**
 * service
 */
public interface ProductCategoryService {
    /**
     * 查找一个，后台用
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查找所有，后台用
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类型查找，用户用
     * @param categoryList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

    /**
     * 添加和修改
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);

}

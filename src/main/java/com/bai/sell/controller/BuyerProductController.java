package com.bai.sell.controller;


import com.bai.sell.entity.ProductCategory;
import com.bai.sell.entity.ProductInfo;
import com.bai.sell.service.ProductCategoryService;
import com.bai.sell.service.ProductInfoService;
import com.bai.sell.utils.ResultUtil;
import com.bai.sell.vo.ProductInfoVO;
import com.bai.sell.vo.ProductVO;
import com.bai.sell.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @author Administrator
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService categoryService;


    @GetMapping("/list")
    public Result list(){
        //1. 查询所有的上架商品
        List<ProductInfo> upAll = productInfoService.findUpAll();

        //2. 查询类目 (一次性查找)
        //List<Integer> categoryTypeList = new ArrayList<>();

        // 传统方法:foreach遍历获取categoryType
        /*for (ProductInfo productInfo: upAll) {
            categoryTypeList.add(productInfo.getCategoryType());

        }*/

        // 精简方法:Lambda表达式获取categoryType
        List<Integer> categoryTypeList = upAll.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3. 数据拼装
        //类目层
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //商品层
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : upAll) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //把productInfo的属性的值copy到productInfoVO里，不用再一个一个set了
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    //添加到list
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        /*//最外层的返回状态
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");

        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();

        //最里层的商品
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        //内层的商品类目
        result.setData(Arrays.asList(productVO));*/

        //把返回的状态封装成util类
      /*  Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(productVOList);
*/
        return ResultUtil.success(productVOList);
    }


}

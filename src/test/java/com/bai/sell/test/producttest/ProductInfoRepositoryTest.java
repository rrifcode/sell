package com.bai.sell.test.producttest;

import com.bai.sell.entity.ProductInfo;
import com.bai.sell.repository.ProductInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void findByProductStatus(){

        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        System.out.println(byProductStatus.toString());
        Assert.assertNotNull(byProductStatus);
    }

    @Test
    public void addTest(){

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("A001");
        productInfo.setProductName("麻辣烫");
        productInfo.setProductPrice(new BigDecimal(18.2));
        productInfo.setProductIcon(null);
        productInfo.setProductDescription("又麻又辣");
        productInfo.setProductStock(5);
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);

        System.out.println(productInfo.toString());
        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);

    }


}
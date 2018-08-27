package com.bai.sell.test.producttest;

import com.bai.sell.entity.ProductInfo;
import com.bai.sell.enums.ProductStatusEnum;
import com.bai.sell.service.impl.ProductInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() {

        ProductInfo productInfo = service.findOne("A001");
        System.out.println(productInfo);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() {

        List<ProductInfo> upAll = service.findUpAll();

        System.out.println(upAll.toString());
        Assert.assertNotEquals("0", upAll);
    }

    @Test
    public void findAll() {

        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> all = service.findAll(pageRequest);
        System.out.println(all);
        Assert.assertNotNull(all);
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("A003");
        productInfo.setProductName("水煮鱼");
        productInfo.setProductPrice(new BigDecimal(39));
        productInfo.setProductIcon(null);
        productInfo.setProductDescription("又鲜又嫩");
        productInfo.setProductStock(3);
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(4);

        ProductInfo save = service.save(productInfo);
        Assert.assertNotNull(save);

    }
}
package com.bai.sell.test.categorytest;

import com.bai.sell.entity.ProductCategory;
import com.bai.sell.service.impl.ProductCategoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService ;

    @Test
    public void findOne() {
        ProductCategory one = productCategoryService.findOne(2);
        System.out.println(one.toString());
        Assert.assertNotNull(one);
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = productCategoryService.findAll();
        System.out.println(all.toString());
        Assert.assertNotNull(all);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> integers = Arrays.asList(2, 3);
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(integers);
        System.out.println(byCategoryTypeIn.toString());
        Assert.assertNotEquals(null, byCategoryTypeIn);
    }

    @Test
    public void save() {

        ProductCategory productCategory = new ProductCategory("销量最高", 5);
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(save);
    }
}
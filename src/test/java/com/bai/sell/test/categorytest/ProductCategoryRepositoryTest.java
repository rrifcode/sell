package com.bai.sell.test.categorytest;

import com.bai.sell.entity.ProductCategory;
import com.bai.sell.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;




@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    /** 测试查找一个 */
    @Test
    public void findOneTet(){

        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());

    }

    /** 测试增加和修改，都是使用save */
    @Test
    public void addTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生喜欢");
        productCategory.setCategoryType(10);
        repository.save(productCategory);

    }

    @Test
    @Transactional  /** 只插入正式数据，测试数据不插入数据库 javax */
    public void addTest2(){
        ProductCategory productCategory = new ProductCategory("女生最爱",5);
        ProductCategory save = repository.save(productCategory);

        Assert.assertNotNull(save);
        Assert.assertNotEquals(null, save);

    }

    /** 查找多个条件 */
    @Test
    public void findByCategoryTypeTest(){
        List<Integer> list = Arrays.asList(1, 2, 3);

        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(list);
        System.out.println(byCategoryTypeIn.toString());
        Assert.assertNotNull(byCategoryTypeIn);


    }

}
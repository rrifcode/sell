package com.bai.sell.repository;

import com.bai.sell.entity.OrderDetail;
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
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;


    @Test
    public void saveTest(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("A00002");
        orderDetail.setOrderId("A00001");
        orderDetail.setProductId("123123");
        orderDetail.setProductName("麻辣烫");
        orderDetail.setProductPrice(new BigDecimal(54.23));
        orderDetail.setProductQuantity(12);
        orderDetail.setProductIcon("http://xxxx.jpg");

        OrderDetail result = repository.save(orderDetail);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> orderDetailList = repository.findByOrderId("A00001");
        //Assert.assertNotNull(orderDetailList);
        //查询的结果的长度不等于0
        Assert.assertNotEquals(0, orderDetailList.size());


    }
}
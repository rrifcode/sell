package com.bai.sell.repository;

import com.bai.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "adc123";


    @Test
    public void save(){

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("A00003");
        orderMaster.setOrderBuyerName("师弟");
        orderMaster.setOrderBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(54.67));
        orderMaster.setOrderBuyerPhone("12345678902");
        orderMaster.setOrderBuyerAddress("深圳");

        OrderMaster result = repository.save(orderMaster);
        Assert.assertTrue(result != null);

    }


    @Test
    public void findByOrderBuyerOpenid() {

        PageRequest request = new PageRequest(0, 3);
        Page<OrderMaster> result = repository.findByOrderBuyerOpenid(OPENID, request);
        //System.out.println(result.getTotalElements());

        Assert.assertNotEquals(0,result.getTotalElements());
    }
}
package com.bai.sell.service.impl;

import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "123123";

    private final String ORDER_ID = "1535161283741364852";

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderBuyerName("猪八戒");
        orderDTO.setOrderBuyerOpenid(BUYER_OPENID);
        orderDTO.setOrderBuyerPhone("12345678902");
        orderDTO.setOrderBuyerAddress("深圳");

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("A003");
        orderDetail.setProductQuantity(1);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("A002");
        orderDetail1.setProductQuantity(2);

        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail1);


        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);

        log.info("<创建订单> result={}", result);

        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() {

        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("查询单个订单 orderDTO={}", orderDTO);
        Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());

    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}
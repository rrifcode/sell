package com.bai.sell.service.impl;

import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderDetail;
import com.bai.sell.enums.OrderStatusEnum;
import com.bai.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        PageRequest pageable = new PageRequest(0, 2);

        Page<OrderDTO> list = orderService.findList(BUYER_OPENID, pageable);

        log.info("查询订单List: orderDTOList={}", list);

        Assert.assertNotNull(list);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO result = orderService.cancel(orderDTO);
        //参数1期待的值, 参数2为结果的值
        //表示结果的值是否和期待的值一致, 一致返回成功, 不一致返回失败
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());

    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO result = orderService.finish(orderDTO);
        //参数1期待的值, 参数2为结果的值
        //表示结果的值是否和期待的值一致, 一致返回成功, 不一致返回失败
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);

        OrderDTO result = orderService.paid(orderDTO);
        //参数1期待的值, 参数2为结果的值
        //表示结果的值是否和期待的值一致, 一致返回成功, 不一致返回失败
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getOrderPayStatus());


    }
}
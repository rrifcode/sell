package com.bai.sell.service.impl;

import com.bai.sell.dto.OrderDTO;
import com.bai.sell.enums.ResultEnum;
import com.bai.sell.exception.SellException;
import com.bai.sell.service.BuyerService;
import com.bai.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家service业务实现类
 * Created by Bai
 * 2018-11-2 17:55
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(null == orderDTO){
            log.info("[取消订单]: 订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    /**
     * 抽取出来的业务逻辑
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDTO checkOrderOwner(String openid, String orderId){
        //避免得到订单号,谁都可以查询, 添加一层检验,只有该订单的用户才能查询
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(null == orderDTO){
            return null;
        }
        //如果订单不属于当前用户,抛出异常
        if(!orderDTO.getOrderBuyerOpenid().equalsIgnoreCase(openid)){
            log.info("[查询订单]: 订单的openid不一致. openid={}, orderId={}",openid, orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }

}

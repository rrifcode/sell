package com.bai.sell.service;

import com.bai.sell.dto.OrderDTO;

/**
 * 买家service业务
 * Created by Bai
 * 2018-11-2 17:49
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne (String openid, String orderId);


    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid,String orderId);
}

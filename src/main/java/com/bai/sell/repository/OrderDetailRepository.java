package com.bai.sell.repository;

import com.bai.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情repository
 * @author Administrator
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 一个订单主表对应多个订单详情表
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);

}

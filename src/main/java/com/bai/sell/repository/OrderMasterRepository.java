package com.bai.sell.repository;

import com.bai.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表repository
 * @author Administrator
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 按照买家openId查询订单
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByOrderBuyerOpenid(String buyerOpenId, Pageable pageable);

}

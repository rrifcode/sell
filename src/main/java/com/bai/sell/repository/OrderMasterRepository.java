package com.bai.sell.repository;

import com.bai.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByOrderBuyerOpenid(String buyerOpenId, Pageable pageable);

}

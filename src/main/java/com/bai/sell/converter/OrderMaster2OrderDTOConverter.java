package com.bai.sell.converter;


import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

/**
 * 转换器
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);

        return orderDTO;

    }
}

package com.bai.sell.converter;


import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 * @author Administrator
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);

        return orderDTO;

    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){

        List<OrderDTO> orderDTOList = orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

        return orderDTOList;
    }
}

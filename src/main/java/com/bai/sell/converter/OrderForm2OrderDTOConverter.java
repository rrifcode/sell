package com.bai.sell.converter;

import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderDetail;
import com.bai.sell.entity.OrderMaster;
import com.bai.sell.enums.ResultEnum;
import com.bai.sell.exception.SellException;
import com.bai.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * java类转换器
 * Created by Bai
 * 2018-10-27 9:46
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        //两个对象的属性名不相同, 不能使用BeanUtils拷贝
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderBuyerName(orderForm.getName());
        orderDTO.setOrderBuyerPhone(orderForm.getPhone());
        orderDTO.setOrderBuyerAddress(orderForm.getAddress());
        orderDTO.setOrderBuyerOpenid(orderForm.getOpenid());

        //使用gson把json转换为list
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception e){
            log.info("[对象转换]: 错误, String={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    e.getLocalizedMessage());
        }

        //购物车里的商品,前端传的是json数据,要转换成list
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }

}

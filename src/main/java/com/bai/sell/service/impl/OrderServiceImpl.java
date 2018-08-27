package com.bai.sell.service.impl;

import com.bai.sell.dto.CartDTO;
import com.bai.sell.dto.OrderDTO;
import com.bai.sell.entity.OrderDetail;
import com.bai.sell.entity.OrderMaster;
import com.bai.sell.entity.ProductInfo;
import com.bai.sell.enums.OrderStatusEnum;
import com.bai.sell.enums.PayStatusEnum;
import com.bai.sell.enums.ResultEnum;
import com.bai.sell.exception.SellException;
import com.bai.sell.repository.OrderDetailRepository;
import com.bai.sell.repository.OrderMasterRepository;
import com.bai.sell.service.OrderService;
import com.bai.sell.service.ProductInfoService;
import com.bai.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        //BigInteger.ZERO等同于0
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品(数量,价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(orderDetail==null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail
                    .getProductQuantity())).add(orderAmount);

            //订单详情入库 前端只会传商品id和商品的数量  搞清楚逻辑
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //把productInfo的属性copy到orderDetail
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3. 写入订单数据库(orderMaster和orderDetail)
        //主订单表一对多订单详表
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setOrderPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4. 下单成功后减库存
        //数据从orderDTO.getOrderDetailList()里面取,
        //虽然可以写在上面的for循环里,但是建议不要把其他的不相关的功能写入到for循环里
        //可以使用Lambda表达式
        //注意:高并发的时候,两个线程同时查出库存够,同时执行这个方法,会出现超卖现象.最后解决:redis的锁机制
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),
                e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(null == orderMaster){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(null == orderDetailList){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterRepository.findByOrderBuyerOpenid(buyerOpenid, pageable);
        //TODO

        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}

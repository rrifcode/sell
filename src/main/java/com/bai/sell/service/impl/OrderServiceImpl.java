package com.bai.sell.service.impl;

import com.bai.sell.converter.OrderMaster2OrderDTOConverter;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
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
        //一张主订单表对应多个订单详表
        OrderMaster orderMaster = new OrderMaster();
        //属性拷贝会把原有的属性值覆盖, 所以要先拷贝,再赋值
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        //属性拷贝后, 初始化的两个值被null覆盖, 需要重新赋值
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

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
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

    /**
     * 查询订单的列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterRepository.findByOrderBuyerOpenid(buyerOpenid, pageable);
        //TODO
        List<OrderMaster> orderMasterList = orderMasters.getContent();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);
        //3个参数
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasters.getTotalElements());

        return orderDTOPage;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态,新下单的订单才能取消,完结或已取消的订单无法取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("[取消订单失败]:订单状态不正确: orderStatus={}, orderId={}", orderDTO.getOrderStatus(), orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateOrderStatus = orderMasterRepository.save(orderMaster);
        if(null == updateOrderStatus){
            log.info("[取消订单失败]:订单状态更新失败, updateOrderStatus={}", updateOrderStatus);
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }

        //订单取消后, 库存返回
        //判断订单里是否有商品
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("[取消订单失败]: 订单里没有商品 orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList =
                orderDTO.getOrderDetailList().stream().map(e ->
            new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果是已支付订单, 退款
        //判断支付状态
        if(orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }

        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("[完结订单]: 订单状态不正确 orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(null == result){
            log.info("[完结订单]: 更新订单状态失败 result={}", result);
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("[支付订单]: 订单状态不正确 orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getOrderPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.info("[支付订单]: 支付状态不正确 orderId={}, orderPayStatus={}", orderDTO.getOrderId(), orderDTO.getOrderPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setOrderPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(null == result){
            log.info("[支付订单]: 修改订单支付状态失败 result={}",  result);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_UPDATE_FAIL);
        }

        return orderDTO;
    }
}

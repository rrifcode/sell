package com.bai.sell.controller;


import com.bai.sell.converter.OrderForm2OrderDTOConverter;
import com.bai.sell.dto.OrderDTO;
import com.bai.sell.enums.ResultEnum;
import com.bai.sell.exception.SellException;
import com.bai.sell.form.OrderForm;
import com.bai.sell.service.BuyerService;
import com.bai.sell.service.OrderService;
import com.bai.sell.utils.ResultUtil;
import com.bai.sell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 买家订单接口
 * Created by Bai
 * 2018-10-26 16:11
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    /**
     * 创建订单    @Valid起表单参数校验作用, 一定要有BindingResult
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public Result<Map<String, String>> create(@Valid OrderForm orderForm,
                                              BindingResult bindingResult){
        //表单校验
        if(bindingResult.hasErrors()){
            log.info("[创建订单]: 参数不正确, orderForm={}", orderForm);
            //抛出异常,并把参数错误的具体信息, 哪个参数错误信息打印出来
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //转换
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        //判断购物车是否为空
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("[创建订单]: 购物车为空, orderDTO={}",orderDTO.getOrderDetailList());
            throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }

        OrderDTO result = orderService.create(orderDTO);

        if(null == result){
            log.info("[创建订单]: 失败, result={}", result);
            throw new SellException(ResultEnum.INSERT_DB_ERROR);
        }

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public Result<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                       //分页值如果不传的话,使用默认值
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size){
        //校验openid是否为空
        if(StringUtils.isEmpty(openid)){
            log.info("[订单列表]: openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //分页对象
        PageRequest pageRequest = new PageRequest(page,size);

        Page<OrderDTO> list = orderService.findList(openid, pageRequest);

        //无论是否有数据, 都要返回列表
        return ResultUtil.success(list.getContent());
    }



    //订单详情(查看单个订单)
    @GetMapping("/detail")
    public Result<OrderDTO> detail(@RequestParam("openid") String openid,
                                   @RequestParam("orderId") String orderId){
        //校验参数
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.info("[订单详情列表]: 参数为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        /*// 不安全的做法,待改进. 任何人都可以查询订单, 只要有orderId.须添加判断
        //判断逻辑跟取消订单一样, 单独写成一个service
        OrderDTO orderDTO = orderService.findOne(orderId);*/

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);

        return ResultUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public Result cancel(@RequestParam("openid") String openid,
                         @RequestParam("orderId") String orderId){
        //校验参数
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.info("[订单详情列表]: 参数为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        // 不安全的做法,待改进
        /*OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO cancel = orderService.cancel(orderDTO);*/

        buyerService.cancelOrder(openid, orderId);
        return ResultUtil.success();
    }


}

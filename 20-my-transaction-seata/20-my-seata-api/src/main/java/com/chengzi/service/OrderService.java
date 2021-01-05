package com.chengzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.domain.Order;
import com.chengzi.dto.OrderDTO;
import com.chengzi.response.ObjectResponse;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {

    List<Map<String, Object>> getAllOrder();

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);

}
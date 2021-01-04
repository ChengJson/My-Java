package com.chengzi.service;

import com.chengzi.dto.OrderDTO;
import com.chengzi.response.ObjectResponse;

public interface OrderService {

    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
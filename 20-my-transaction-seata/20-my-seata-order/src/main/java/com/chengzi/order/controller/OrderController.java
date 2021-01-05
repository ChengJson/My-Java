package com.chengzi.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.domain.Order;
import com.chengzi.dto.OrderDTO;
import com.chengzi.order.mapper.OrderMapper;
import com.chengzi.service.AccountService;
import com.chengzi.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {


    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderService orderService;

    @Reference
    private AccountService accountService;

    @RequestMapping("/getAllOrder")
    public Object getAllOrder(){
        List<Map<String, Object>> maps = orderService.getAllOrder();
        return maps;
    }

    @RequestMapping("/deleteOrderById")
    public Object deleteById(Integer id){
        int i = mapper.deleteById(id);
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }

    @RequestMapping("/testOrderTransaction")
    @Transactional
    public Object testTransaction(Integer id){
        int i = mapper.deleteById(id);
        int j = i / 0;
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }


    @RequestMapping("/createOrder")
    @Transactional
    public Object createOrder(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderAmount(1312);
        orderDTO.setCommodityCode("1231321321");
        orderDTO.setOrderCount(2);
        orderDTO.setUserId("chengzi");
        orderService.createOrder(orderDTO);
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<>());
        return maps;
    }

}

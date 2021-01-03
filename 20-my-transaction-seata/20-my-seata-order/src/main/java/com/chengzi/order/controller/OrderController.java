package com.chengzi.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.domain.Order;
import com.chengzi.order.mapper.OrderMapper;
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

    @RequestMapping("/getAllOrder")
    public Object getAllOrder(){
        List<Map<String, Object>> maps = mapper.selectMaps(new QueryWrapper<Order>());
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

}

package com.chengzi.storage.controller;


import com.chengzi.dto.OrderDTO;
import com.chengzi.response.ObjectResponse;
import org.apache.dubbo.config.annotation.Reference;
import com.chengzi.dto.BusinessDTO;
import com.chengzi.service.AccountService;
import com.chengzi.service.BusinessService;
import com.chengzi.service.OrderService;
import com.chengzi.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class BussinessController {
    @Autowired
    BusinessService businessService;

    @Reference
    AccountService accountService;

    @Reference
    StorageService storageService;

    @Reference
    OrderService orderService;

    /**
     *
     *  用户购买商品的业务逻辑。整个业务逻辑由3个微服务提供支持：
     *  仓储服务：对给定的商品扣除仓储数量。
     *  订单服务：根据采购需求创建订单。
     *  帐户服务：从用户帐户中扣除余额。
     *
     * 测试seata
     * @return
     */
    @ResponseBody
    @RequestMapping("/testseata")
    public Object testseata(BusinessDTO businessDTO){

        ObjectResponse<OrderDTO> purchase = businessService.purchase(businessDTO);
        return purchase;
    }

    @RequestMapping("/getAllData")
    public Object getAllData(Model model){

        List<Map<String, Object>> allOrder = orderService.getAllOrder();
        List<Map<String, Object>> allAccount = accountService.getAllAccount();
        List<Map<String, Object>> allStorage = storageService.getAllStorage();

        model.addAttribute("allOrder",allOrder);
        model.addAttribute("allAccount",allAccount);
        model.addAttribute("allStorage",allStorage);
        return "list";
    }



}

package com.chengzi.storage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.dto.BusinessDTO;
import com.chengzi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BussinessController {
    @Autowired
    BusinessService businessService;

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
    @RequestMapping("/testseata")
    public Object testseata(BusinessDTO businessDTO){

        businessService.purchase(businessDTO);
        return "ok";
    }



}

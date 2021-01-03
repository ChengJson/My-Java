package com.chengzi.storage.service;

import com.chengzi.service.BusinessService;
import com.chengzi.service.OrderService;
import com.chengzi.service.StorageService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 主业务逻辑
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Reference
    private StorageService storageService;

    @Reference
    private OrderService orderService;

    /**
     * 采购
     */
    public void purchase(String userId, String commodityCode, int orderCount) {

        /**
         * 减库存
         */
        storageService.deduct(commodityCode, orderCount);

        /**
         * 创建订单并账户减钱
         */
        orderService.create(userId, commodityCode, orderCount);
    }
}

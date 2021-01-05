package com.chengzi.storage.service;


import com.chengzi.dto.BusinessDTO;
import com.chengzi.dto.CommodityDTO;
import com.chengzi.dto.OrderDTO;
import com.chengzi.enums.RspStatusEnum;
import com.chengzi.exception.DefaultException;
import com.chengzi.response.ObjectResponse;
import com.chengzi.service.BusinessService;
import com.chengzi.service.OrderService;
import com.chengzi.service.StorageService;
import io.seata.core.context.RootContext;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

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
     *  {
     *     "userId":"1",
     *     "commodityCode":"C201901140001",
     *     "name":"fan",
     *     "count":2,
     *     "amount":"100"
     * }
     *
     *
     */
    @GlobalTransactional
    public ObjectResponse purchase(BusinessDTO businessDTO) {

        System.out.println("开始全局事务，XID = " + RootContext.getXID());


        ObjectResponse<Object> objectResponse = new ObjectResponse<>();


        businessDTO.setAmount(100);
        businessDTO.setCommodityCode("C201901140001");
        businessDTO.setUserId("zhangsan");
        businessDTO.setCount(2);



        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        //用户id
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        ObjectResponse<OrderDTO> response = orderService.createOrder(orderDTO);

        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        ObjectResponse storageResponse = storageService.decreaseStorage(commodityDTO);

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }

        if (storageResponse.getStatus() != 200 || response.getStatus() != 200) {
            throw new DefaultException(RspStatusEnum.FAIL);
        }

        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(response.getData());
        return objectResponse;
    }
}

package com.chengzi.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengzi.domain.Order;
import com.chengzi.domain.Storage;
import com.chengzi.dto.AccountDTO;
import com.chengzi.dto.OrderDTO;
import com.chengzi.enums.RspStatusEnum;
import com.chengzi.order.mapper.OrderMapper;
import com.chengzi.response.ObjectResponse;
import com.chengzi.service.AccountService;
import com.chengzi.service.OrderService;
import com.chengzi.service.StorageService;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private OrderMapper orderDAO;

    @Reference
    private AccountService accountService;

    @Reference
    private StorageService storageService;

    @Autowired
    OrderMapper orderMapper;


    /**
     * 查询所有的订单数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllOrder(){
        List<Map<String, Object>> maps = orderMapper.selectMaps(new QueryWrapper<Order>());
        return maps;
    }



    /**
     *  创建订单 账户减钱
     * @param
     * @return
     */
    @Override
    @Transactional
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        ObjectResponse<OrderDTO> response = new ObjectResponse<>();
        //扣减用户账户
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(orderDTO.getUserId());
        accountDTO.setAmount(orderDTO.getOrderAmount());
        ObjectResponse objectResponse = accountService.decreaseAccount(accountDTO);

        //生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        //生成订单
        Order tOrder = new Order();
        tOrder.setId(UUID.randomUUID().toString().replace("-",""));
        //用户id
        tOrder.setUserId(orderDTO.getUserId());
        //商品型号
        tOrder.setCommodityCode(orderDTO.getCommodityCode());
        //商品数量
        tOrder.setCount(orderDTO.getOrderCount());
        //商品金额
        tOrder.setMoney(orderDTO.getOrderAmount());
        try {
            baseMapper.createOrder(tOrder);
        } catch (Exception e) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

        if (objectResponse.getStatus() != 200) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.SUCCESS.getCode());
        response.setMessage(RspStatusEnum.SUCCESS.getMessage());
        return response;
    }
}
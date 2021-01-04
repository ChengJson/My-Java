package com.chengzi.order.service;

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
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private OrderMapper orderDAO;

    @Reference
    private AccountService accountService;

    /**
     *  创建订单 账户减钱
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @return
     */

    /**
     * 计算金额
     * @param commodityCode
     * @param orderCount
     * @return
     */
    private int calculate(String commodityCode, int orderCount) {
        return 8888;
    }

    @Override
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
        BeanUtils.copyProperties(orderDTO,tOrder);
        tOrder.setCount(orderDTO.getOrderCount());
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
package com.chengzi.order.service;

import com.chengzi.domain.Order;
import com.chengzi.order.mapper.OrderMapper;
import com.chengzi.service.AccountService;
import com.chengzi.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

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
    @Transactional
    public int create(String userId, String commodityCode, int orderCount) {

        int orderMoney = calculate(commodityCode, orderCount);

        /**
         * 记账
         */
        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);

        // INSERT INTO orders ...
        return orderDAO.insert(order);
    }

    /**
     * 计算金额
     * @param commodityCode
     * @param orderCount
     * @return
     */
    private int calculate(String commodityCode, int orderCount) {
        return 8888;
    }
}
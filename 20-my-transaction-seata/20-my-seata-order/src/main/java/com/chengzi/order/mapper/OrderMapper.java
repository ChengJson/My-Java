package com.chengzi.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengzi.domain.Order;
import org.apache.ibatis.annotations.Param;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/28 18:00
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 创建订单
     * @Param:  order 订单信息
     * @Return:
     */
    void createOrder(@Param("order") Order order);
}

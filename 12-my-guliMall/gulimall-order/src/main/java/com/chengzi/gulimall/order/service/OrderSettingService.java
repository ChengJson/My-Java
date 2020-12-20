package com.chengzi.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.common.utils.PageUtils;
import com.chengzi.gulimall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * ??????????Ϣ
 *
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 17:42:27
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


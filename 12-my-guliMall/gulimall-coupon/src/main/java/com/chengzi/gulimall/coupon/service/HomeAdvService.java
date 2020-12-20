package com.chengzi.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.common.utils.PageUtils;
import com.chengzi.gulimall.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * ??ҳ?ֲ????
 *
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 17:20:22
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


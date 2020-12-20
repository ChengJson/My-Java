package com.chengzi.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.common.utils.PageUtils;
import com.chengzi.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * ????&???Է???????
 *
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 15:51:46
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


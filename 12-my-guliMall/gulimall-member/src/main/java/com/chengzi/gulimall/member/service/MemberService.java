package com.chengzi.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengzi.common.utils.PageUtils;
import com.chengzi.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员表
 *
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 17:34:29
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


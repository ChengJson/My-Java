package com.chengzi.gulimall.member.dao;

import com.chengzi.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员表
 * 
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 17:34:29
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

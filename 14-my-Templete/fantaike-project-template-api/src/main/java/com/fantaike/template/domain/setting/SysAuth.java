package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 权限信息表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:37
 */
@Data
@TableName("sys_auth")
public class SysAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@TableId(type=IdType.AUTO)
	private Long authId;
	/**
	 * 权限名称
	 */
	private String authName;
	/**
	 * 权限状态
	 */
	private String status;
	/**
	 * 
	 */
	private String createUsr;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 最新变更用户
	 */
	private String updateUsr;
	/**
	 * 最新变更时间
	 */
	private Date updateTime;

}

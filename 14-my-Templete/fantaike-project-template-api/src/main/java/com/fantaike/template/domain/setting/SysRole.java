package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 角色信息表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:36
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色id
	 */
	@TableId(type=IdType.AUTO)
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 状态 a-生效,i-失效,w-待生效
	 */
	private String status;
	/**
	 * 备注
	 */
	private String roleRmk;
	/**
	 * 创建人
	 */
	private String createUsr;
	/**
	 * 创建时间
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

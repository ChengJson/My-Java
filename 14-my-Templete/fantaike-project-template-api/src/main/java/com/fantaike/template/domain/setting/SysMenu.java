package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 菜单表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:36
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单id
	 */
	@TableId(type=IdType.AUTO)
	private Long menuId;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单地址
	 */
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;
	/**
	 * 类型   0：目录   1：菜单   2：按钮
	 */
	private Integer type;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 菜单状态 Y：启用，N：禁用
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

	/**
	 * 子菜单列表，展示树状结构时使用，不对应数据库字段
	 */
	@TableField(exist = false)
	private List<SysMenu> children;
}

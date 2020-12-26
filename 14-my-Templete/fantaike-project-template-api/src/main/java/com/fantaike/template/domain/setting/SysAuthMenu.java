package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 权限菜单表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:36
 */
@Data
@TableName("sys_auth_menu")
public class SysAuthMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@TableId(type=IdType.AUTO)
	private Long id;
	/**
	 * 菜单id
	 */
	private Long menuId;
	/**
	 * 权限id
	 */
	private Long authId;

}

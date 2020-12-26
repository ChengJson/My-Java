package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 角色权限表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:36
 */
@Data
@TableName("sys_role_auth")
public class SysRoleAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	@TableId(type=IdType.AUTO)
	private Long id;

	/**角色id*/
	private Long roleId;

	/**权限id*/
	private Long authId;

}

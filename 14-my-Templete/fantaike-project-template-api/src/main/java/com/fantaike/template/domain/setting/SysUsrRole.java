package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户角色对应表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:37
 */
@Data
@TableName("sys_usr_role")
public class SysUsrRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 金融机构代码
	 */
	@TableId(type=IdType.AUTO)
	private Long id;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 用户id
	 */
	private String usrId;
}

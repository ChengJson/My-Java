package com.fantaike.template.domain.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息表
 * @author 吴桂镇
 * @email wuguizhen@fantaike.ai
 * @date 2019-06-20 16:14:36
 */
@Data
@TableName("sys_usr")
public class SysUsr implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 登录用户名
	 */
	@TableId(type=IdType.INPUT)
	private String usrId;
	/**
	 * 用户姓名
	 */
	private String usrName;
	/**
	 * 证件类型
	 */
	private String usrIdTyp;
	/**
	 * 证件号码
	 */
	private String usrIdNo;
	/**
	 * 密码
	 */
	private String usrPassword;
	/**
	 * 手机号码
	 */
	private String usrTel;
	/**
	 * 电子邮箱
	 */
	private String usrEmail;
	/**
	 * 状态A-生效,I-失效,W-待生效
	 */
	private String status;
	/**
	 * 备注
	 */
	private String usrRmk;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建用户
	 */
	private String createUsr;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 变更用户
	 */
	private String updateUsr;

}

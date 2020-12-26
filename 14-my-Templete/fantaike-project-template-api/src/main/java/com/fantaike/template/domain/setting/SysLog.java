package com.fantaike.template.domain.setting;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @ClassName: SysLog
 * @Description: 系统日志
 * @Author: wuguizhen
 * @Date: 2019/7/11 11:05
 * @Version: v1.0 文件初始创建
 */
@Data
public class SysLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 模块名称
	 */
	private String module;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 响应时间
	 */
	private Integer time;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 位置
	 */
	private String location;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;

}

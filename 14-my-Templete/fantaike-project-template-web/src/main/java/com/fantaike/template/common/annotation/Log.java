package com.fantaike.template.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Log
 * @Description: 自定义系统操作日志注解
 * @Author: wuguizhen
 * @Date: 2019/7/11 12:56
 * @Version: v1.0 文件初始创建
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	/** 模块名称 */
	String module() default "";

	/** 操作名称 */
	String operation() default "";
}

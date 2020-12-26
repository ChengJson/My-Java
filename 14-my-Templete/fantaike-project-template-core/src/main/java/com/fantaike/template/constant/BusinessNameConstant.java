package com.fantaike.template.constant;

/**
 * @ClassName: BusinessNameConstant
 * @Description: 业务名称常量，用于日志输出时定义businessName，通常一个模块设置一个即可，例：用户管理
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:24
 * @Version: v1.0 文件初始创建
 */
public class BusinessNameConstant {

    /**
     * 用户管理模块业务名称，用于相应的web项目controller和service
     */
    public static final String SETTING_USER = "用户管理";

    /**
     * 角色管理模块业务名称，用于相应的web项目controller和service
     */
    public static final String SETTING_ROLE = "角色管理";

    /**
     * 菜单管理模块业务名称，用于相应的web项目controller和service
     */
    public static final String SETTING_MENU = "菜单管理";

    /**
     * 权限管理模块业务名称，用于相应的web项目controller和service
     */
    public static final String SETTING_AUTH = "权限管理";

    /**
     * 用户登陆模块业务名称
     */
    public static final String LOGIN = "登录";

    /**
     * 统一异常处理模块
     */
    public static final String EXCEPTION_HANDLE = "异常处理";

    /**
     * 常量类不允许 new
     */
    private BusinessNameConstant() {

    }
}

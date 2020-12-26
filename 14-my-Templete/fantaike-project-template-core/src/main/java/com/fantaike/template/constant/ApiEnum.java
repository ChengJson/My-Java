package com.fantaike.template.constant;

/**
 * @ClassName: ApiEnum
 * @Description: 接口返回码值 相关枚举类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:17
 * @Version: v1.0 文件初始创建
 */
public enum ApiEnum {

    /***
     * 请求成功
     */
    RSLT_CDE_000000("请求成功", "000000"),

    RSLT_CDE_000001("请求参数有误", "000001"),
    /**
     * 登录失败
     */
    RSLT_CDE_001001("用户未注册", "001001"),
    RSLT_CDE_001002("密码错误", "001002"),
    RSLT_CDE_001003("该帐户不可用（未激活或删除）", "001003"),
    RSLT_CDE_001004("登录状态失效", "001004"),
    RSLT_CDE_001005("用户无此权限", "001005"),
    RSLT_CDE_001006("token异常","001006"),


    /**
     * 用户管理相关模块 002000 - 002999
     */
    RSLT_CDE_002001("不能修改用户本身", "002001"),
    RSLT_CDE_002002("用户已存在", "002002"),
    RSLT_CDE_002003("用户不存在", "002003"),
    RSLT_CDE_002004("角色已存在", "002004"),
    RSLT_CDE_002005("角色不存在", "002005"),
    RSLT_CDE_002006("权限已存在", "002005"),
    RSLT_CDE_002007("权限不存在", "002006"),

    /***
     * 其他错误
     */
    RSLT_CDE_999999("其他错误", "999999");

    private String name;
    private String code;


    ApiEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * @Description: 通过code获取名称
     * @param code 枚举内code
     * @Date: 2019/7/8 20:17
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String getName(String code) {
        for (ApiEnum br : ApiEnum.values()) {
            if (br.getCode().equals(code)) {
                return br.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

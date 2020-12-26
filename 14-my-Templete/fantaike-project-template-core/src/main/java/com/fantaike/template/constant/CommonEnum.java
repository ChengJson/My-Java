package com.fantaike.template.constant;

/**
 * @ClassName: CommonEnum
 * @Description: 接口返回码值 相关枚举类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:17
 * @Version: v1.0 文件初始创建
 */
public enum CommonEnum {

    /**
     * 状态： 生效
     */
    STAT_ACTIVE("生效状态", "A"),
    STAT_INACTIVE("失效状态", "I"),

    /**
     * 权限类型
     */
    MENU_TYPE_DIRECTORY("菜单类型-目录", "0"),
    MENU_TYPE_MENU("菜单类型-菜单", "1"),
    MENU_TYPE_BUTTON("菜单类型-按钮", "2");

    private String name;
    private String code;


    CommonEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * @Description: 通过code获取名称
     * @param code 枚举code
     * @Date: 2019/7/8 20:17
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String getName(String code) {
        for (CommonEnum br : CommonEnum.values()) {
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

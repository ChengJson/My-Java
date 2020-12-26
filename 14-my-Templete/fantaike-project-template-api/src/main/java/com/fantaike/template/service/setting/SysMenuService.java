package com.fantaike.template.service.setting;

import com.fantaike.template.domain.setting.SysMenu;

import java.util.List;

/**
 * @ClassName: SysMenuService
 * @Description: 菜单表接口
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:59
 * @Version: v1.0 文件初始创建
 */
public interface SysMenuService {

    /**
     * @Description: 查询所有生效的菜单，并进行格式化成树状结构
     * @param 
     * @Date: 2019/7/8 19:40
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    List<SysMenu> getMenuTreeList();

}

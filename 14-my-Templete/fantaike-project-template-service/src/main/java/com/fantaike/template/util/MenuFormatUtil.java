package com.fantaike.template.util;

import com.fantaike.template.domain.setting.SysMenu;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MenuFormatUtil
 * @Description: 菜单格式化操作
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:43
 * @Version: v1.0 文件初始创建
 */
@NoArgsConstructor
public class MenuFormatUtil {

   /**
    * @Description: 菜单格式化操作
    * @param menuList 菜单列表，未格式化之前
    * @Date: 2019/7/8 19:43
    * @Author: wuguizhen
    * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
    * @Throws
    */
    public static List<SysMenu> formatToTreeList(List<SysMenu> menuList) {
        List<SysMenu> menuTreeList = new ArrayList<>();
        //循环所有菜单信息，把顶级菜单添加到列表中,并使用递归找到他的所有子菜单
        for (SysMenu menu : menuList) {
            //如果菜单是目录则查询他的子菜单，并加到列表中
            if (menu.getParentId() == 0) {
                menu.setChildren(getChildren(menu.getMenuId(), menuList));
                menuTreeList.add(menu);
            }
        }
        return menuTreeList;
    }

    /**
     * @Description: 找到菜单列表中 所有属于父菜单id下的菜单
     * @param parentMenuId 父菜单id
     * @param menuList 全量菜单列表
     * @Date: 2019/7/8 19:44
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    private static List<SysMenu> getChildren(long parentMenuId, List<SysMenu> menuList) {
        List<SysMenu> menuTree = new ArrayList<>();
        //循环所有菜单信息，把顶级菜单添加到列表中,并使用递归找到他的所有子菜单
        for (SysMenu menu : menuList) {
            if (parentMenuId == menu.getParentId()) {
                menu.setChildren(getChildren(menu.getMenuId(), menuList));
                menuTree.add(menu);
            }
        }
        if (menuTree.size() == 0) {
            return new ArrayList<>();
        }
        return menuTree;
    }

}

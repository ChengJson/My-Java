package com.fantaike.template.controller.setting;

import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.service.setting.SysMenuService;
import com.fantaike.template.util.CommonLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MenuController
 * @Description: 菜单设置
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:38
 * @Version: v1.0 文件初始创建
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    /***引用菜单表服务*/
    @Reference
    private SysMenuService smenuService;


    /**
     * @Description: 查询所有生效的菜单，并进行格式化成树状结构
     * @param 
     * @Date: 2019/7/8 19:51
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping("/list/tree")
    public JsonResponse list() {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_MENU, "获取菜单列表");
        return JsonResponse.success(smenuService.getMenuTreeList());
    }
}

package com.fantaike.template.controller.setting;

import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.dto.setting.RoleDetailDTO;
import com.fantaike.template.service.setting.SysRoleService;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.validation.InsertGroup;
import com.fantaike.template.validation.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @ClassName: RoleController
 * @Description: 角色设置
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:44
 * @Version: v1.0 文件初始创建
 */
@Validated
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    /**
     * 引用角色信息表服务
     */
    @Reference
    private SysRoleService sRoleService;


    /**
     * @Description: 获取角色列表，并根据页码和每页的容量进行分页查询
     * @param roleName 角色名称，不为空时按角色名称为条件查询，否则不进行条件查询
     * @param page 页码
     * @param pageSize 页容量
     * @Date: 2019/7/8 19:45
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("role:list")
    public JsonResponse getRoleList(String roleName, Integer page, Integer pageSize) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_ROLE, "查询角色列表,pageNum:" + page + ";pageSize：" + pageSize);
        return sRoleService.listObjectsByPage(roleName, page, pageSize);
    }

    /**
     * @Description: 获取角色详情，关联权限表获取该角色的所有权限
     * @param roleId 角色id 不能为空
     * @Date: 2019/7/8 19:46
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping(value = "/get")
    @RequiresPermissions("role:get")
    public JsonResponse getRole(@NotNull(message = "角色代码为空") Long roleId) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_ROLE, "查询角色详情,角色编号：" + roleId);
        return sRoleService.getRoleDetail(roleId);
    }

    /**
     * @Description: 新增角色，保存角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("role:save")
    public JsonResponse insertRole(@Validated(InsertGroup.class) RoleDetailDTO roleDetailDTO, @RequestParam(required = false) List<Long> authIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_ROLE, "新增角色信息：" + roleDetailDTO.toString());
        roleDetailDTO.setUpdateUsr(optUsrId);
        return sRoleService.createRole(roleDetailDTO, authIds);
    }

    /**
     * @Description: 修改角色，修改角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/update")
    @RequiresPermissions("role:update")
    public JsonResponse updateRole(@Validated(UpdateGroup.class) RoleDetailDTO roleDetailDTO, @RequestParam(required = false) List<Long> authIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_ROLE, "修改角色信息：" + roleDetailDTO.toString());
        roleDetailDTO.setUpdateUsr(optUsrId);
        return sRoleService.updateRole(roleDetailDTO, authIds);
    }

    /**
     * @Description: 将角色状态置位生效和失效
     * @param roleId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/changeStatus")
    @RequiresPermissions("role:changeStatus")
    public JsonResponse changeRoleStatus(@NotNull(message = "角色代码不能为空") Long roleId,
                                         @NotNull(message = "角色状态不能为空") @Pattern(regexp = "^[A|I]$", message = "角色状态不合法") String sts) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_ROLE, "修改角色编号：" + roleId + " 所对应角色状态为：" + sts);
        return sRoleService.changeStatus(roleId, sts, optUsrId);
    }

}

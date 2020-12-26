package com.fantaike.template.controller.setting;

import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.annotation.Log;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.dto.setting.AuthDetailDTO;
import com.fantaike.template.service.setting.SysAuthService;
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
 * @ClassName: AuthController
 * @Description: 权限设置相关接口
 * @Author: wuguizhen
 * @Date: 2019/7/5 18:33
 * @Version: v1.0 文件初始创建
 */
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**引用权限服务*/
    @Reference
    private SysAuthService sauthService;


    /**
     * @Description: 获取权限列表，根据分页数据进行查询
     * @param selectParam 查询条件
     * @param page 分页页码
     * @param pageSize 分页容量
     * @Date: 2019/7/5 18:28
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Log(module = BusinessNameConstant.SETTING_AUTH,operation = "列表查询")
    @GetMapping("/list")
    @RequiresPermissions("auth:list")
    public JsonResponse list(SysAuth selectParam, Integer page, Integer pageSize) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_AUTH, "查询权限列表,pageNum:" + page + ";pageSize：" + pageSize + ",查询条件为：" + selectParam.toString());
        return sauthService.listObjectsByPage(selectParam, page, pageSize);
    }

    /**
     * @Description: 查询权限详情
     * @param authId 权限id
     * @Date: 2019/7/5 18:29
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Log(module = BusinessNameConstant.SETTING_AUTH,operation = "详情查询")
    @GetMapping("/get")
    @RequiresPermissions("auth:get")
    public JsonResponse get(@NotNull(message = "权限id不能为空") Long authId) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_AUTH, "查询权限详情,权限编号：" + authId);
        return sauthService.getAuthDetail(authId);
    }

    
    /**
     * @Description: 权限保存 保存权限信息表 和 权限菜单关联表
     * @param authDetailDTO 需要添加的权限详细信息
     * @param menuIds 需要添加的权限所属的菜单id 例：1,2,3
     * @Date: 2019/7/5 18:29
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Log(module = BusinessNameConstant.SETTING_AUTH,operation = "保存")
    @PostMapping("/save")
    @RequiresPermissions("auth:save")
    public JsonResponse save(@Validated(InsertGroup.class) AuthDetailDTO authDetailDTO, @RequestParam(required = false) List<Long> menuIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_AUTH, "查询信息保存：" + authDetailDTO.toString());
        authDetailDTO.setUpdateUsr(optUsrId);
        return sauthService.createAuth(authDetailDTO, menuIds);
    }

    /**
     * @Description: 修改权限信息
     * @param authDetailDTO 需要修改的权限详情
     * @param menuIds 需要修改的权限所对应的菜单id  例：1,2,3
     * @Date: 2019/7/5 18:31
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Log(module = BusinessNameConstant.SETTING_AUTH,operation = "修改")
    @PostMapping(value = "/update")
    @RequiresPermissions("auth:update")
    public JsonResponse updateRole(@Validated(UpdateGroup.class) AuthDetailDTO authDetailDTO, @RequestParam(required = false) List<Long> menuIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_AUTH, "修改权限信息：" + authDetailDTO.toString());
        authDetailDTO.setUpdateUsr(optUsrId);
        return sauthService.updateRole(authDetailDTO, menuIds);
    }

    /**
     * @Description:将权限状态置位生效和失效
     * @param authId 权限编号
     * @param sts 权限状态,取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/5 18:32
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @Log(module = BusinessNameConstant.SETTING_AUTH,operation = "状态修改")
    @PostMapping(value = "/changeStatus")
    @RequiresPermissions("auth:changeStatus")
    public JsonResponse changeRoleStatus(@NotNull(message = "权限代码不能为空") Long authId,
                                         @NotNull(message = "权限状态不能为空") @Pattern(regexp = "^[A|I]$", message = "权限状态不合法") String sts) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_AUTH, "修改权限编号：" + authId + " 所对应状态为：" + sts);
        return sauthService.changeStatus(authId, sts, optUsrId);
    }

}

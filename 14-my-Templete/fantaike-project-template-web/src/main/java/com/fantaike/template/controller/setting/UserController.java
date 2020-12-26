package com.fantaike.template.controller.setting;

import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.constant.BusinessNameConstant;
import com.fantaike.template.domain.setting.SysUsr;
import com.fantaike.template.dto.setting.UserDetailDTO;
import com.fantaike.template.service.setting.SysUsrService;
import com.fantaike.template.util.CommonLogger;
import com.fantaike.template.validation.InsertGroup;
import com.fantaike.template.validation.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户设置
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:55
 * @Version: v1.0 文件初始创建
 */
@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {

    /** 引用用户服务 */
    @Reference
    private SysUsrService susrService;

    /**
     * @Description: 获取角色列表，并根据页码和每页的容量进行分页查询
     * @param param 用户查询参数
     * @param page 页码
     * @param pageSize 页容量
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping(value = "/list")
    @RequiresPermissions("user:list")
    public JsonResponse getUserList(SysUsr param, Integer page, Integer pageSize) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_USER, "查询用户列表,page:" + page + ";pageSize：" + pageSize);
        return susrService.listObjectsByPage(param, page, pageSize);
    }
    
    /**
     * @Description: 获取用户详情
     * @param usrId 用户编号
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping(value = "/get")
    @RequiresPermissions("user:get")
    public JsonResponse getUser(@NotBlank(message = "用户账户为空") String usrId) {
        CommonLogger.info(ShiroUserAccountUtil.getUserId(), BusinessNameConstant.SETTING_USER, "查询用户信息：" + usrId);
        return susrService.getUserDetail(usrId);
    }

    /**
     * @Description: 添加用户
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("user:save")
    public JsonResponse createUser(@Validated(InsertGroup.class) UserDetailDTO userDetailDTO, @RequestParam(required = false) List<Long> roleIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "添加用户信息：" + userDetailDTO.toString());
        userDetailDTO.setUpdateUsr(optUsrId);
        return susrService.createUser(userDetailDTO, roleIds);
    }

    /**
     * @Description: 修改用户信息
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/update")
    @RequiresPermissions("user:update")
    public JsonResponse updateUser(@Validated(UpdateGroup.class) UserDetailDTO userDetailDTO, @RequestParam(required = false) List<Long> roleIds) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "修改用户信息：" + userDetailDTO.toString());
        userDetailDTO.setUpdateUsr(optUsrId);
        return susrService.updateUser(userDetailDTO, roleIds);
    }

    /**
     * @Description: 将用户状态置位生效和失效
     * @param usrId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @PostMapping(value = "/changeStatus")
    @RequiresPermissions("user:changeStatus")
    public JsonResponse changeUserStatus(@NotBlank(message = "用户账户为空") String usrId,
                                         //用户状态参数不为空且 必须是 A 或 I
                                         @NotBlank(message = "用户状态为空") @Pattern(regexp = "^[A|I]$", message = "用户状态不合法") String sts) {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "将" + usrId + "用户状态修改为：" + sts);
        //如果被修改的角色为当前角色则不允许修改
        if (optUsrId.equals(usrId)) {
            CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "操作用户不允许修改自己的状态");
            return JsonResponse.response(ApiEnum.RSLT_CDE_002001);
        }
        return susrService.changeStatus(usrId, sts, optUsrId);
    }
    
    /**
     * @Description: 获取登录用户的生效的菜单信息并格式化成树结构
     * @param 
     * @Date: 2019/7/8 19:58
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    @GetMapping("/getMenusByUsrId")
    public JsonResponse getMenusByUsrId() {
        String optUsrId = ShiroUserAccountUtil.getUserId();
        CommonLogger.info(optUsrId, BusinessNameConstant.SETTING_USER, "获取用户菜单列表");
        return JsonResponse.success(susrService.listMenuByUsrIdFormat(optUsrId));
    }
}

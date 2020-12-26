package com.fantaike.template.service.setting;

import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.dto.setting.RoleDetailDTO;
import com.fantaike.template.exception.BusinessException;

import java.util.List;

/**
 * @ClassName: SysRoleService
 * @Description: 角色信息表接口
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:49
 * @Version: v1.0 文件初始创建
 */
public interface SysRoleService {

    /**
     * @Description: 修改角色，修改角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse updateRole(RoleDetailDTO roleDetailDTO, List<Long> authIds) throws BusinessException;

    /**
     * @Description: 新增角色，保存角色信息表和角色权限关联表
     * @param roleDetailDTO 角色相信信息
     * @param authIds 权限id列表 格式为 例： 1,2,3
     * @Date: 2019/7/8 19:47
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse createRole(RoleDetailDTO roleDetailDTO, List<Long> authIds) throws BusinessException;

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
    JsonResponse listObjectsByPage(String roleName, Integer page, Integer pageSize);

    /**
     * @Description: 获取角色详情，关联权限表获取该角色的所有权限
     * @param roleId 角色id 不能为空
     * @Date: 2019/7/8 19:46
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse getRoleDetail(Long roleId);

    /**
     * @Description: 将角色状态置位生效和失效
     * @param roleId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse changeStatus(Long roleId, String sts, String optUsrId);
}

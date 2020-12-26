package com.fantaike.template.service.setting;

import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysUsr;
import com.fantaike.template.dto.setting.UserDetailDTO;
import com.fantaike.template.exception.BusinessException;

import java.util.List;

/**
 * @ClassName: SysUsrService
 * @Description: 用户信息表接口定义
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:03
 * @Version: v1.0 文件初始创建
 */
public interface SysUsrService {

    /**
     * @Description: 修改用户信息
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse updateUser(UserDetailDTO userDetailDTO, List<Long> roleIds);

    /**
     * @Description: 添加用户
     * @param userDetailDTO 用户信息
     * @param roleIds 角色id列表，格式为，例： 1,2,3
     * @Date: 2019/7/8 19:56
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse createUser(UserDetailDTO userDetailDTO, List<Long> roleIds) throws BusinessException;

    /**
     * @Description: 获取用户详情
     * @param usrId 用户编号
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse getUserDetail(String usrId);

    /**
     * @Description: 获取角色列表，并根据页码和每页的容量进行分页查询
     * @param selectParam 用户查询参数
     * @param page 页码
     * @param pageSize 页容量
     * @Date: 2019/7/8 19:55
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse listObjectsByPage(SysUsr selectParam, Integer page, Integer pageSize);

    /**
     * @Description: 将用户状态置位生效和失效
     * @param usrId 角色id
     * @param sts 修改成的状态 取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @param optUsrId 操作用户id
     * @Date: 2019/7/8 19:48
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse changeStatus(String usrId, String sts, String optUsrId);

    /**
     * @Description: 根据用户id进行查询
     * @param usrId 用户id
     * @Date: 2019/7/8 20:05
     * @Author: wuguizhen
     * @Return com.fantaike.template.domain.setting.SysUsr
     * @Throws
     */
    SysUsr getByUsrId(String usrId);

    /**
     * @return java.util.List<com.fantaike.template.domain.setting.SysRole>
     * @Author wugz
     * @Description 根据用户id查询其角色列表
     * @Date 2019/6/21 13:40
     * @Param [usrId]
     */
    List<SysRole> listRoleByUsrId(String usrId);

    /**
     * @Description: 获取用户的生效的菜单信息
     * @param usrId 用户id
     * @Date: 2019/7/8 19:58
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    List<SysMenu> listMenuByUsrId(String usrId);

    /**
     * @Description: 获取用户的生效的菜单信息并格式化成树结构
     * @param usrId 用户id
     * @Date: 2019/7/8 20:06
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    List<SysMenu> listMenuByUsrIdFormat(String usrId);
}

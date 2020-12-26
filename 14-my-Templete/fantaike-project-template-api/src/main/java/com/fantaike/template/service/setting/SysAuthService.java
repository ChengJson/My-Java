package com.fantaike.template.service.setting;


import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.dto.setting.AuthDetailDTO;

import java.util.List;

/**
 * @ClassName: SysAuthService
 * @Description: 权限信息表服务接口
 * @Author: wuguizhen
 * @Date: 2019/7/8 19:27
 * @Version: v1.0 文件初始创建
 */
public interface SysAuthService {

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
    JsonResponse listObjectsByPage(SysAuth selectParam, Integer page, Integer pageSize);

    /**
     * @Description: 查询权限详情
     * @param authId 权限id
     * @Date: 2019/7/5 18:29
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse getAuthDetail(Long authId);

    /**
     * @Description: 权限保存 保存权限信息表 和 权限菜单关联表
     * @param authDetailDTO 需要添加的权限详细信息
     * @param menuIds 需要添加的权限所属的菜单id 例：1,2,3
     * @Date: 2019/7/5 18:29
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse createAuth(AuthDetailDTO authDetailDTO, List<Long> menuIds);

    /**
     * @Description: 修改权限信息
     * @param authDetailDTO 需要修改的权限详情
     * @param menuIds 需要修改的权限所对应的菜单id  例：1,2,3
     * @Date: 2019/7/5 18:31
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse updateRole(AuthDetailDTO authDetailDTO, List<Long> menuIds);

    /**
     * @Description:将权限状态置位生效和失效
     * @param authId 权限编号
     * @param sts 权限状态,取值为CommonEnum.STAT_ACTIVE 和 CommonEnum.STAT_INACTIVE
     * @Date: 2019/7/5 18:32
     * @Author: wuguizhen
     * @Return com.fantaike.template.api.JsonResponse
     * @Throws
     */
    JsonResponse changeStatus(Long authId, String sts, String optUsrId);
}

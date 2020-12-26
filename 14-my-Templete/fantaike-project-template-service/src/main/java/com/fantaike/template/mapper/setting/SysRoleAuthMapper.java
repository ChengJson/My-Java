package com.fantaike.template.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.domain.setting.SysRoleAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: SysRoleAuthMapper
 * @Description: 角色权限表
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:24
 * @Version: v1.0 文件初始创建
 */
public interface SysRoleAuthMapper extends BaseMapper<SysRoleAuth> {

    /**
     * @Description: 根据角色id查询对应权限
     * @param roleId 角色id
     * @param activeStatus 生效状态
     * @Date: 2019/7/8 20:24
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysAuth>
     * @Throws
     */
    List<SysAuth> listByRoleId(@Param("roleId") Long roleId, @Param("status") String activeStatus);
}

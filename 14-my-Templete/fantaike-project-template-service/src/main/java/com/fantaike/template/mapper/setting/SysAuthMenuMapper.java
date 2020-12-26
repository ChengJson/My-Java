package com.fantaike.template.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantaike.template.domain.setting.SysAuthMenu;
import com.fantaike.template.domain.setting.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: SysAuthMenuMapper
 * @Description: 权限菜单表
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:23
 * @Version: v1.0 文件初始创建
 */
public interface SysAuthMenuMapper extends BaseMapper<SysAuthMenu> {

    /**
     * @Description: 根据权限id 查询对应菜单
     * @param authId 权限id
     * @param activeStatus 生效状态
     * @Date: 2019/7/8 20:23
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    List<SysMenu> listMenuByAuthId(@Param("authId") Long authId, @Param("status") String activeStatus);
}

package com.fantaike.template.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysUsrRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: SysUsrRoleMapper
 * @Description: 用户角色表
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:25
 * @Version: v1.0 文件初始创建
 */
public interface SysUsrRoleMapper extends BaseMapper<SysUsrRole> {

    /**
     * @Description: 根据用户编号查询角色信息
     * @param usrId 用户id
     * @param activeStatus 生效状态
     * @Date: 2019/7/8 20:25
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysRole>
     * @Throws
     */
    List<SysRole> listRoleByUsrId(@Param("usrId") String usrId, @Param("status") String activeStatus);

    /**
     * @Description: 根据用户编号查询其所对应的菜单
     * @param usrId 用户id
     * @param activeStatus 生效状态
     * @Date: 2019/7/8 20:26
     * @Author: wuguizhen
     * @Return java.util.List<com.fantaike.template.domain.setting.SysMenu>
     * @Throws
     */
    List<SysMenu> listMenuByUsrId(@Param("usrId") String usrId, @Param("status") String activeStatus);
}

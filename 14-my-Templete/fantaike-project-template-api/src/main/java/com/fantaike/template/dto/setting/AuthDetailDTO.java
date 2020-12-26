package com.fantaike.template.dto.setting;

import com.fantaike.template.domain.setting.SysMenu;
import com.fantaike.template.validation.InsertGroup;
import com.fantaike.template.validation.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: AuthDetailDTO
 * @Description: 权限详细信息，与前端交互时使用
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:26
 * @Version: v1.0 文件初始创建
 */
@Data
public class AuthDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(groups = {UpdateGroup.class})
    private Long authId;
    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空",groups = {InsertGroup.class})
    private String authName;
    /**
     * 权限状态
     */
    private String status;
    /**
     *
     */
    private String createUsr;
    /**
     *
     */
    private Date createTime;
    /**
     * 最新变更用户
     */
    private String updateUsr;
    /**
     * 最新变更时间
     */
    private Date updateTime;

    /**
     * 权限对应菜单列表
     */
    private List<SysMenu> menuList;
}

package com.fantaike.template.dto.setting;

import com.fantaike.template.domain.setting.SysAuth;
import com.fantaike.template.validation.InsertGroup;
import com.fantaike.template.validation.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: RoleDetailDTO
 * @Description: 角色详细信息，在与前端进行角色详情交互时返回
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:25
 * @Version: v1.0 文件初始创建
 */
@Data
public class RoleDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     **/
    @NotNull(groups = {UpdateGroup.class})
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空",groups = {InsertGroup.class})
    private String roleName;

    /**
     * 角色状态
     **/
    private String status;

    /**
     * 备注
     */
    private String roleRmk;

    /**
     * 创建人
     */
    private String createUsr;

    /**
     * 创建时间
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
     * 权限信息
     */
    private List<SysAuth> authList;

}

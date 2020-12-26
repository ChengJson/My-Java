package com.fantaike.template.dto.setting;

import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.validation.InsertGroup;
import com.fantaike.template.validation.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserDetailDTO
 * @Description: 用户详细信息，与前端交互时使用
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:25
 * @Version: v1.0 文件初始创建
 */
@Data
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户名
     */
    @NotNull(message = "用户编号不能为空",groups = {UpdateGroup.class,InsertGroup.class})
    private String usrId;
    /**
     * 用户姓名
     */
    @NotNull(message = "用户姓名不能为空",groups = {InsertGroup.class})
    private String usrName;
    /**
     * 证件类型
     */
    private String usrIdTyp;
    /**
     * 证件号码
     */
    private String usrIdNo;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空",groups = {InsertGroup.class})
    private String usrPassword;
    /**
     * 手机号码
     */
    private String usrTel;
    /**
     * 电子邮箱
     */
    @Email
    private String usrEmail;
    /**
     * 状态A-生效,I-失效,W-待生效
     */
    private String status;
    /**
     * 备注
     */
    private String usrRmk;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建用户
     */
    private String createUsr;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 变更用户
     */
    private String updateUsr;

    /**
     * 用户角色列表
     */
    private List<SysRole> roleList;


}

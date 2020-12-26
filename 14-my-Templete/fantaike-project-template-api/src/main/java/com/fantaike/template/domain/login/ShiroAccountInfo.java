package com.fantaike.template.domain.login;

import com.fantaike.template.domain.setting.SysRole;
import com.fantaike.template.domain.setting.SysUsr;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @ClassName: ShiroAccountInfo
 * @Description: 用户登录信息存放实体
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:28
 * @Version: v1.0 文件初始创建
 */
@Data
public class ShiroAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public ShiroAccountInfo(SysUsr sUsr) {
        this.usrId = sUsr.getUsrId();
        this.usrName = sUsr.getUsrName();
        this.usrIdTyp = sUsr.getUsrIdTyp();
        this.usrIdNo = sUsr.getUsrIdNo();
        this.usrPassword = sUsr.getUsrPassword();
        this.usrTel = sUsr.getUsrTel();
        this.usrEmail = sUsr.getUsrEmail();
        this.status = sUsr.getStatus();
        this.createUsr = sUsr.getCreateUsr();
        this.createTime = sUsr.getCreateTime();
        this.updateUsr = sUsr.getUpdateUsr();
        this.updateTime = sUsr.getUpdateTime();
    }

    /**
     * 登录用户名
     */
    private String usrId;
    /**
     * 用户姓名
     */
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
    private String usrPassword;
    /**
     * 手机号码
     */
    private String usrTel;
    /**
     * 电子邮箱
     */
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
     * 角色列表
     */
    private List<SysRole> roleList;

}

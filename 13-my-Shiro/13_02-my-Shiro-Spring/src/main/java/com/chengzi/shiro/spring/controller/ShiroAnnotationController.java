package com.chengzi.shiro.spring.controller;

import com.chengzi.shiro.spring.beans.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 15:20
 */
@RestController
public class ShiroAnnotationController {

    /**
     * 需要管理员角色和用户管理角色才能访问这个接口
     * @return
     */
    @RequiresRoles({"fater","user1"})
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    public String testRole(){
        return "You have Role Admin and user Role";
    }


    /**
     * 需要管理员角色和用户管理角色才能访问这个接口
     * @return
     */
    @RequiresPermissions({"user:add1","user:delete2"})
    @RequestMapping(value = "/testPre",method = RequestMethod.GET)
    public String testPremission(){
        return "You have user add and delete premission";
    }
}
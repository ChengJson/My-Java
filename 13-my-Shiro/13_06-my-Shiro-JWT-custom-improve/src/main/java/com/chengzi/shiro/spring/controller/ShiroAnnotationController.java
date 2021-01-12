package com.chengzi.shiro.spring.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    @RequiresRoles({"admin","user"})
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    public String testRole(){
        return "You have Role Admin and user Role";
    }


    /**
     * 需要管理员角色和用户管理角色才能访问这个接口
     * @return
     */
    @RequiresPermissions({"user:add","user:delete"})
    @RequestMapping(value = "/testPre",method = RequestMethod.GET)
    public String testPremission(){
        return "You have user add and delete premission";
    }


    @RequestMapping(value = "/testCustomFilter",method = RequestMethod.GET)
    public String testCustomFilter(){
        return "You have user add and delete premission";
    }
}
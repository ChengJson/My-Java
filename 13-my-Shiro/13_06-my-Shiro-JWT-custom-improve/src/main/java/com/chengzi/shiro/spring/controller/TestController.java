package com.chengzi.shiro.spring.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 17:39
 */
@RestController
public class TestController {

    //@RequiresRoles({"admin","user"})
    @RequestMapping("/test")
    @RequiresPermissions({"user:add"})
    public String test(){
        return "ok";
    }
}

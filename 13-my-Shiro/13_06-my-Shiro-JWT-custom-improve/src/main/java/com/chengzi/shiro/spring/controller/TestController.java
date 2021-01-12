package com.chengzi.shiro.spring.controller;

import com.chengzi.shiro.spring.shiroconfig.jwt.ISyncCacheService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 17:39
 */
@RestController
public class TestController {

    @Autowired
    ISyncCacheService syncCacheService;

    //@RequiresRoles({"admin","user"})
    @RequestMapping("/test")
    @RequiresPermissions({"user:add"})
    public String test(){
        return "ok";
    }

    @RequestMapping("/getlock")
    @ResponseBody
    public Boolean getlock(){
        Boolean lock = syncCacheService.getLock("lock", 0);
        return lock;
    }
}

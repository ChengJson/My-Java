package com.chengzi.shiro.spring.controller;

import com.chengzi.shiro.spring.beans.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 13:00
 */
@RestController
public class UserController {

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST)
    public String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(user.getUsername(),user.getPassword()));
        return "Login Success";
    }
}

package com.chengzi.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;



    @ResponseBody
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("token")String token){
        System.out.println("=======getuserinfo=====");
        String username = redisTemplate.opsForValue().get(token);
        return username;
    }

    @GetMapping("/login.html")
    public String hello(@RequestParam("redirect_url") String redirectUrl,
                        Model model,
                        @CookieValue(value = "sso_token",required = false)String token){
        if (!StringUtils.isEmpty(token)) {
            return "redirect:" + redirectUrl + "?token=" + token;
        }
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
    }


    @PostMapping("/dologin")
    public String employees(@RequestParam("username")String username,
                            @RequestParam("password")String password,
                            @RequestParam("url")String redirectUrl,
                            HttpServletResponse response){


        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            String uuid = UUID.randomUUID().toString().replace("-", "");
            redisTemplate.opsForValue().set(uuid,username);

            Cookie sso_token = new Cookie("sso_token", uuid);
            response.addCookie(sso_token);
            return "redirect:" + redirectUrl + "?token=" + uuid;
        }else {
            return "login";
        }

    }
}

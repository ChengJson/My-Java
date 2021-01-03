package com.chengzi.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class HelloController {


    @Value("${sso.server.url}")
    private String serverUrl;
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    /**
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/boss")
    public String employees(Model model, HttpSession session,
                            @RequestParam(value = "token",required = false) String token){
        if(!StringUtils.isEmpty(token)){
            //如果携带token说明是从登录服务器跳转会来的
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8080/userInfo?token=" + token, String.class);
            String body = forEntity.getBody();

            session.setAttribute("loginUser",body);
        }

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:" +serverUrl + "?redirect_url=http://localhost:8082/boss";
        }


        ArrayList<String> emps = new ArrayList<>();
        emps.add("张三");
        emps.add("李四");
        model.addAttribute("emps",emps);
        return "list";
    }
}

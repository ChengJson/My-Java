package com.chengzi.controller;

import com.chengzi.config.ConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Value("${url.alipay}")
    private String alipayUrl;

    @Value("${url.weixinpay}")
    private String wxPay;

    @Autowired
    private ConfigInfo configInfo;

    @RequestMapping("/sayhi")
    public @ResponseBody String sayHi(){
        return "hi springboot";
    }

    @RequestMapping("/customConfig")
    public @ResponseBody String customConfig(){
        return alipayUrl + "}{" + wxPay;
    }

    @RequestMapping("/configInfo")
    public @ResponseBody ConfigInfo configInfo(){
        return configInfo;
    }


}

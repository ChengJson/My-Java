package com.chengzi.controller;

import com.chengzi.config.ConfigInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JspController {

    @RequestMapping("/index")
    public  String configInfo() {
        return "index";
    }
}

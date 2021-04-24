package com.wkcto.springcloud.controller;

import com.wkcto.springcloud.service.Helloservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    Helloservice helloservice;

    /**
     * 调用声明式的接口方法，实现对远程服务的调用
     * @return
     */
    @RequestMapping("/web/hello")
    public String hello(){
        return helloservice.hello();
    }
}

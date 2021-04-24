package com.wkcto.springcloud.fallback;

import com.wkcto.springcloud.service.Helloservice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class MyFallback implements Helloservice {


    @Override
    public String hello() {
        return "发生异常了";
    }
}
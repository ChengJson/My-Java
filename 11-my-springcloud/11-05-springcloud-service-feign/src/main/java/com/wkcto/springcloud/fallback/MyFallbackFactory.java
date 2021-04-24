package com.wkcto.springcloud.fallback;

import com.wkcto.springcloud.service.Helloservice;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class MyFallbackFactory implements FallbackFactory<Helloservice> {


    @Override
    public Helloservice create(Throwable throwable) {

        return new Helloservice() {
            @Override
            public String hello() {
                return throwable.getMessage();
            }
        };
    }
}
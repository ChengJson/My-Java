package com.wkcto.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @LoadBalanced//使用ribbon实现负载均衡的调用
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 随机策略
     * @return
     */
    //@Bean
    public IRule iRule(){
        //先轮询，如果不可用再转到可用的
        new RetryRule();
        return new RandomRule();
    }
}

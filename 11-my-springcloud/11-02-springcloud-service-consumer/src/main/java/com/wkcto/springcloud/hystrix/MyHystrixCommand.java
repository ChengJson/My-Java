package com.wkcto.springcloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 *
 * 自定义的Hystrix请求
 *
 */
public class MyHystrixCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    protected MyHystrixCommand(Setter setter,RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        //调用远程的服务
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hystrix", String.class).getBody();
    }

    /**
     * 当远程服务超时，异常，不可用等情况时，会触发该熔断方法
     *
     * @return
     */
    public String getFallback(){
        Throwable executionException = super.getExecutionException();
        String message = executionException.getMessage();
        System.out.println(message);
        //实现服务熔断/降级逻辑
        return "自定义熔断触发。。。";
    }

}

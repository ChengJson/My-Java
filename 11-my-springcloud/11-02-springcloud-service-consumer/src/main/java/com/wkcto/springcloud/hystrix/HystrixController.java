package com.wkcto.springcloud.hystrix;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wkcto.springcloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试断路器
 */
@RestController
public class HystrixController {

    @Autowired
    RestTemplate restTemplate;


    /**
     * 超时
     *
     * @return
     */
    @RequestMapping("/web/hystrix")
    @HystrixCommand(fallbackMethod = "error", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "1500")}) //熔断器，调用不通，回调 error()方法
    public String hystrix() {
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hystrix", String.class).getBody();
    }

    /**
     * 服务降级方法
     *
     * @return
     */
    public String error(Throwable throwable) {
        return "error";
    }

    /**
     * 远程服务发生异常也能拿到信息
     *
     * @return
     */
    @RequestMapping("/web/hystrixThrowable")
    @HystrixCommand(fallbackMethod = "throwable", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "1500")}) //熔断器，调用不通，回调 error()方法
    public String hystrixThrowable() {
        int a = 1 / 0;
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hystrix", String.class).getBody();
    }


    /**
     * 可以拿到异常信息
     *
     * @param throwable
     * @return
     */
    public String throwable(Throwable throwable) {
        return throwable.getMessage();
    }


    /**
     * 忽略RuntimeException
     * RuntimeException不触发熔断
     * @return
     */
    @RequestMapping("/web/ignore")
    @HystrixCommand(fallbackMethod = "ignore",
            ignoreExceptions = RuntimeException.class,
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "1500")}) //熔断器，调用不通，回调 error()方法
    public String ignore() {
        int a = 1 / 0;
        return restTemplate.getForEntity("http://01-SPRINGCLOUD-SERVICE-PROVIDER/service/hystrix", String.class).getBody();
    }


    /**
     *  自定义hystrix
     */
    @RequestMapping("/web/myHystrixCommand")
    public String hystrix2() {
        MyHystrixCommand myHystrixCommand = new MyHystrixCommand(
                com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(""))
                , restTemplate);
        String execute = myHystrixCommand.execute();
        return execute;
    }

    /**
     *  自定义hystrix异步
     */
    @RequestMapping("/web/myHystrixCommand2")
    public String hystrix3() {
        MyHystrixCommand myHystrixCommand = new MyHystrixCommand(
                com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(""))
                , restTemplate);
        Future<String> queue = myHystrixCommand.queue();
        String s = null;
        try {
            s = queue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return s;
    }


}

package com.chengzi.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、使用rabbitmq，引入amqp RabbitAutoConfiguration就会自动生效
 * 2、给容器自动配置了连接工厂、RabbitMessagingTemplate等组件
 * 3、给配置文件中配置 spring.rabbitmq
 * 4、开启自动配置注解
 *
 *
 */
@EnableRabbit
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

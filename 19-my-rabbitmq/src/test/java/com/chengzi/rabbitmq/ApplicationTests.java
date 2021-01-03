package com.chengzi.rabbitmq;


import com.chengzi.rabbitmq.beans.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public  class ApplicationTests {

    @Autowired
    AmqpAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
    }

    /***
     * 1、如何创建Exchange Queue、Binding
     *      1）使用AmqpAdmin创建
     *
     * 2、如何收发消息
     *
     */
    @Test
    public void createExchange(){
        DirectExchange directExchange = new DirectExchange("my-direct-exchange", true, false);
        rabbitAdmin.declareExchange(directExchange);
        log.info("exchange[{}]创建成功","my-direct-exchange");
    }

    /**
     * 创建队列
     */
    @Test
    public void createQueue(){
        Queue queue = new Queue("my-hello-queue", true, false, false);
        rabbitAdmin.declareQueue(queue);
        log.info("queue[{}]创建成功","my-hello-queue");
    }


    /**
     * 创建绑定
     */
    @Test
    public void createBinding(){
        Binding binding = new Binding("my-hello-queue", Binding.DestinationType.QUEUE, "my-direct-exchange", "hello", null);
        rabbitAdmin.declareBinding(binding);
        log.info("bingding[{}]创建成功","");
    }


    @Test
    public void sendMessage(){

        Student zhangsan = new Student("zhangsan", 123);
        rabbitTemplate.convertAndSend("my-direct-exchange","hello",zhangsan);
        log.info("消息发送完成");
    }


    /**
     * 创建绑定：测试自动确认
     */
    @Test
    public void createAckBinding(){
        Queue queue = new Queue("my-ack-queue", true, false, false);
        rabbitAdmin.declareQueue(queue);
        log.info("queue[{}]创建成功","my-ack-queue");
        Binding binding = new Binding("my-ack-queue", Binding.DestinationType.QUEUE, "my-direct-exchange", "ack", null);
        rabbitAdmin.declareBinding(binding);
        log.info("bingding[{}]创建成功","");
    }
}

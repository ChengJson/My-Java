package com.chengzi.rabbitmq.listener;


import com.chengzi.rabbitmq.beans.Student;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.junit.Test;

@Slf4j
@Component
public class MyRabbitListener {


    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 参数可以写以下几种类型
     * 1、Message 原生消息 消息头+消息体 详细信息
     * 2、T<消息实体> 自动转换
     * 3、channel 当前传输数据的通道
     *
     *   可以有很多人都来监听，只要收到消息，队列删除消息，而且只能有一个收到此消息
     *
     *   场景：
     *      1）、服务启动多个，相当于启动了多个监听者，每一条消息只能被一个消费者消费
     *      2）、只有一个消息完全处理完，方法运行结束，才能接收下一条消息、线程睡眠来模拟业务处理
     * @param message
     * @param student
     * @param channel
     */
    @RabbitListener(queues = {"my-hello-queue"})
    public void reciveMessage(Message message,
                              Student student,
                              Channel channel){
        System.out.println("第一种方式接收到消息...." + student);
        byte[] body = message.getBody();

        MessageProperties messageProperties = message.getMessageProperties();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("第一种方式消息处理完成" + student.getUsername());
    }

}

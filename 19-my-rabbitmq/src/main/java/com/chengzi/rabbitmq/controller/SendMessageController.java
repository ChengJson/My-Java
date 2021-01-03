package com.chengzi.rabbitmq.controller;

import com.chengzi.rabbitmq.beans.Student;
import com.chengzi.rabbitmq.beans.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.VariableElement;
import java.util.UUID;

@Slf4j
@RestController
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public Object sendMessage(){

        for(int i = 1;i<=10;i++){
            Student zhangsan = new Student("zhangsan", 123);
            if (i %2 == 0) {
                Teacher teacher = new Teacher("123", "123", "132");
                rabbitTemplate.convertAndSend("my-direct-exchange","hello",teacher);
            } else {
                zhangsan.setUsername(zhangsan.getUsername()+i);
                rabbitTemplate.convertAndSend("my-direct-exchange","hello",zhangsan);
            }

        }
        log.info("消息发送完成");
        return "OK";
    }

    @RequestMapping("/testerror")
    public Object sendMessageEerror(){

        for(int i = 1;i<=10;i++){
            Student zhangsan = new Student("zhangsan", 123);
            if (i %2 == 0) {
                /**************/
                /*使用错误的建***/
                /**************/
                rabbitTemplate.convertAndSend("my-direct-exchange","hello123",zhangsan);
            } else {
                zhangsan.setUsername(zhangsan.getUsername()+i);
                rabbitTemplate.convertAndSend("my-direct-exchange","hello",zhangsan);
            }
        }
        log.info("消息发送完成");
        return "OK";
    }

    /**
     * 实际使用 将消息发送到mq之后，还需要将消息按照uuid存储在数据库中，消费消息后 设置为已经消费，没有消费的可以重新发送
     * @return
     */
    @RequestMapping("/testuuid")
    public Object testuuid(){

        for(int i = 1;i<=10;i++){
            Student zhangsan = new Student("zhangsan", 123);
            String uuid = UUID.randomUUID().toString();
            if (i % 2 == 0) {
                /**************/
                /*使用错误的建***/
                /**************/
                rabbitTemplate.convertAndSend("my-direct-exchange","hello123",zhangsan,new CorrelationData(uuid));
            } else {
                zhangsan.setUsername(zhangsan.getUsername()+i);
                rabbitTemplate.convertAndSend("my-direct-exchange","hello",zhangsan,new CorrelationData(uuid));
            }
        }
        log.info("消息发送完成");
        return "OK";
    }


    /**
     * 测试手动确认
     * @return
     */
    @RequestMapping("/testack")
    public Object testack(){

        for(int i = 1;i<=10;i++){
            Student zhangsan = new Student("zhangsan", 123);
            String uuid = UUID.randomUUID().toString();

            zhangsan.setUsername(zhangsan.getUsername()+i);
            rabbitTemplate.convertAndSend("my-direct-exchange","ack",zhangsan,new CorrelationData(uuid));

        }
        log.info("消息发送完成");
        return "OK";
    }
}

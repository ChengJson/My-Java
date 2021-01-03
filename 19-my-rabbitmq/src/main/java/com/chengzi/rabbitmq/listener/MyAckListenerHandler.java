package com.chengzi.rabbitmq.listener;


import com.chengzi.rabbitmq.beans.Student;
import com.chengzi.rabbitmq.beans.Teacher;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = {"my-ack-queue"})
public class MyAckListenerHandler {


    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 接收消息类型是学生的消息 测试手动确认
     * @param message
     * @param student
     * @param channel
     */
    @RabbitHandler
    public void reciveMessage(Message message,
                              Student student,
                              Channel channel) throws IOException {
        System.out.println("测试ACK接收到消息...." + student);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("deliveryTag===>" + deliveryTag) ;

//        try {
//            //断点方式、停止服务模拟宕机 还是会自动全部确认
//            channel.basicAck(deliveryTag,false);
//        } catch (IOException e) {
//            //网络中断，确认信号没有发出
//        }

//        if (deliveryTag % 2 == 0) {
//            channel.basicAck(deliveryTag,false);
//        } else {
//            System.out.println("不签收===>" + deliveryTag);
//        }

        if (deliveryTag % 2 == 0) {
            channel.basicAck(deliveryTag,false);
        } else {
            /**
             * 加入程序执行到这里宕机，即下面的代码不执行，那么就是相当于拒绝，并且重新入队参数requeue 为true
             *
             */

            //第三个参数为是否重新入队，重新入队，tag又从0开始，false相当于丢弃
            channel.basicNack(deliveryTag,false,true);
            //第二个参数为是否重新入队
            //channel.basicReject(deliveryTag,false);
            System.out.println("不签收===>" + deliveryTag);
        }

        System.out.println("测试ACK消息处理完成" + student.getUsername());
    }

}

package com.chengzi.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitMqConfig {

    private static Logger logger = LoggerFactory.getLogger(MyRabbitMqConfig.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter  messageContext(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制 RabbitTemplate
     * 1、服务器收到消息回调
     *      1）、spring.rabbitmq.publisher-confirms=true
     *      2）、设置确认回调
     *
     * 2、 消息正确抵达队列进行回调
     *     1）、spring.rabbitmq.publisher-returns=true
     *          spring.rabbitmq.template.mandatory=true
     *     2）、设置returnCallback
     *
     * 3、消费端确认（保证每个消息被正确消费，此时才可以从brocker删除这个消息）
     *     1）、默认是自动确认的，只要消息接收到，客户端会自动确认，服务端会移除这个消息
     *     2）、问题：如果只有一个消息接收成功了，宕机了，发生消息丢失
     *     3）、手动确认，否则一批消息只要消息一抵达 全部自动确认了，只要没有确认，消息就一直是unacked状态，
     *          即使connsumer宕机，消息不会丢失，会重新变为Ready，下一次，下一次有新的consumer连接进来就发给它
     *     4）、spring.rabbitmq.listener.simple.acknowledge-mode=manual
     *
     *     如何确认？
     *
     */

    //MyRabbitMqConfig创建完成后，就执行这个方法
    @PostConstruct
    public void initRabbitTemplate(){

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             *  只要消息抵达服务器,ack就等于ture
             * @param correlationData 当前消息的唯一关联数据（消息的唯一id）
             * @param ack               消息时候成功收到
             * @param cause               失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("confrim....correlationData[" + correlationData + "]===ack["+ ack +"]===cause[" + cause + "]" );
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message       投递消息的详细信息
             * @param replaycode    回复的状态码值
             * @param replayText    回复的文本内容
             * @param exchange      当时这个消息发送给哪个交换机
             * @param routeingkey   当时这个消息用的哪个路由键
             *
             *
             *         测试使用错误的路由建
             */
            @Override
            public void returnedMessage(Message message, int replaycode, String replayText, String exchange, String routeingkey) {
                logger.info("fail....message[" + message + "]===replaycode["+ replaycode +"]===replayText[" + replayText + "]==[exchange" + exchange + "]==routeingkey[" + routeingkey + "]" );
            }
        });
    }


}

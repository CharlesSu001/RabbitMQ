/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestConsumerChina
 * Author:   苏晨宇
 * Date:     2020/12/8 21:30
 * Description: 专门接受china.*消息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.how2java.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈专门接受china.*消息〉
 *
 * @author 苏晨宇
 * @create 2020/12/8
 * @since 1.0.0
 */
public class TestConsumer4China {
    public final static String EXCHANGE_NAME="topics_exchange";
    public static void main(String[] args) throws IOException, TimeoutException {
        //消费者取名
        final String name="consumer-china";

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("123.57.173.77");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //交换机声明
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //获取临时队列
        String queueName=channel.queueDeclare().getQueue();

        //接受china信息
        channel.queueBind(queueName,EXCHANGE_NAME,"china.*");
        System.out.println(name+"等待接收消息");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " 接收到消息 '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(queueName, true, consumer);
    }
}
 

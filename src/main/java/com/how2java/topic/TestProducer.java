/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestProducer
 * Author:   苏晨宇
 * Date:     2020/12/8 21:21
 * Description: 生产者 生产4个主题 国家 种类 两两组合
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.how2java.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 〈一句话功能简述〉<br>
 * 〈生产者 生产4个主题 国家 种类 两两组合〉
 *
 * @author 苏晨宇
 * @create 2020/12/8
 * @since 1.0.0
 */
public class TestProducer {
    public final static String EXCHANGE_NAME = "topics_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("123.57.173.77");
        //创建新连接
        Connection connection = factory.newConnection();

        //创建新通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String[] routing_keys = new String[]{"usa.news", "usa.weather", "china.news", "china.weather"};
        String[] messages = new String[]{"美国新闻", "美国天气", "中国新闻", "中国天气"};

        for (int i = 0; i < routing_keys.length; i++) {
            String routingKey = routing_keys[i];
            String message = messages[i];
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.printf("发送消息到路由 %s 内容是%s %n", routingKey, message);
        }

        //关闭通道和链接
        channel.close();
        connection.close();
    }
}
 

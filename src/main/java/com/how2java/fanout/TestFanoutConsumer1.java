/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestFanoutCustomer
 * Author:   苏晨宇
 * Date:     2020/12/8 20:35
 * Description: fanout模式接收消息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.how2java.fanout;

import cn.hutool.core.util.RandomUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * 〈一句话功能简述〉<br>
 * 〈fanout模式接收消息〉
 *
 * @author 苏晨宇
 * @create 2020/12/8
 * @since 1.0.0
 */
public class TestFanoutConsumer1 {
    public final static String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //为当前消费者起名字
        final String name = "consumer-" + RandomUtil.randomString(5);

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("123.57.173.77");


        //创建新连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //交换机声明（交换机名称：交换机类型）
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列与交换机绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(name + "等待接收消息");

        //通知服务器需要频道消息，如果有消息 执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                String message = new String(body, "UTF-8");
                System.out.println(name + "接收到消息 '" + message + "'");
            }
        };
        //自动回复队列应答
        channel.basicConsume(queueName, true, consumer);
    }
}
 

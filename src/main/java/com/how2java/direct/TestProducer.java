/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestProducer
 * Author:   苏晨宇
 * Date:     2020/12/8 21:13
 * Description: 生产100条消息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.how2java.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈生产100条消息〉
 *
 * @author 苏晨宇
 * @create 2020/12/8
 * @since 1.0.0
 */
public class TestProducer {
    public final static String QUEUE_NAME = "direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("123.57.173.77");


        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();

        for (int i = 0; i < 100; i++) {
            String message = "direct消息" + i;
            //发送消息到队列
            channel.basicPublish("", QUEUE_NAME,null, message.getBytes("UTF-8"));
            System.out.println("发送消息：" + message);
        }

        //关闭通道和链接
        channel.close();
        connection.close();

    }
}
 

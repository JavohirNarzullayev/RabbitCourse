package org.example.lesson1;/* 
 @author: Javohir
  Date: 2/12/2022
  Time: 12:52 PM*/

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private final static String QUEUE_NAME = "suxrob_javas";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        /*factory.setHost("192.168.1.224");
        factory.setPort(80);
        factory.setUsername("suxrobjon");
        factory.setPassword("2a55f70a841f18b97c3a7db939b7adc9e34a0f1b");
        factory.setVirtualHost("suxrob");*/
        factory.setUri("amqp://suxrobjon:2a55f70a841f18b97c3a7db939b7adc9e34a0f1b@192.168.1.224/suxrob");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            int count=0;
            do {
                String message = "Hello World From Java!---->"+count;

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                ++count;
               /* Thread.sleep(4_00);*/
            }while (true);

        }
    }

}

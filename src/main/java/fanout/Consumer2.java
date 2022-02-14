package fanout;/* 
 @author: Javohir
  Date: 2/14/2022
  Time: 1:46 PM*/

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Consumer2 {

    private final static String QUEUE_NAME = "test_qt2";
    private final static String key = "color.green";
    private final static String DirectExchange="java_directly";



    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://javohir:2a55f70a841f18b97c3a7db939b7adc9e34a0f1b@localhost:5672/javahost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                Thread.sleep(4_00);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

}

package directly.multiconsumer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private final static String QUEUE_NAME = "test_qd1";
    private final static String key = "color.green";
    private final static String key2 = "color.blue";
    private final static String DirectExchange="java_directly";

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://javohir:2a55f70a841f18b97c3a7db939b7adc9e34a0f1b@localhost:5672/javahost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            int count=0;
            do {
                String message = "For queue it key color green Java!---->"+count;


                channel.basicPublish(DirectExchange, key, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                ++count;
                 Thread.sleep(4_00);
            }while (count<10);

            do {
                String message = "For queue it key color blue Java!---->"+count;
                channel.basicPublish(DirectExchange, key2, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                ++count;
                Thread.sleep(4_00);
            }while (count<21);

        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}

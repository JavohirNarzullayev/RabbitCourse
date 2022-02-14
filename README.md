##RabbitMq server ga ulanib olish uchun configuratsiyalar
```java
   public static ConnectionFactory getConnectionFactory() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://USERNAME:PASSWORD@HOST:PORT/VHOST");
        return factory;
    }
```

#Directly

##Talab etiladigan konfiguratsiyalar
- Virtual host yaratish rabbitmq orqali maxsus
- User yaratish va unga permissionlarni berish
- Queue yaratish Quareum type   ex(test_qd1)
- Exchange type yaratish va uni tipini Directly qilamiz
- Queularimizni bind  qilishimiz kerak yaratgan exchange type va key beramiz


##Qabul qiluvchi (Consumer)

![img.png](img.png)

```java
public class Consumer {

    private final static String QUEUE_NAME = "test_qd1";
    private final static String key = "color.green";//key
    private final static String DirectExchange = "java_directly";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        //avtomatik tasdiqlash uchun
        boolean autoack = true;
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

}
```
##Uzatuvchi(Producer)
```java
public class Consumer {

    private final static String QUEUE_NAME = "test_qd1";
    private final static String key = "color.green";
    private final static String DirectExchange="java_directly";



    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

}
```


































#Fanout

##Talab etiladigan konfiguratsiyalar
- Virtual host yaratish rabbitmq orqali maxsus
- User yaratish va unga permissionlarni berish
- Queue yaratish Quareum type orqali bir nechta ex(test_qf1,test_qf2,...)
- Exchange type yaratish va uni tipini Fanout qilamiz
- Queularimizni bind  qilishimiz kerak 


##Qabul qiluvchi(Consumer) 
```java
public class ConsumerN {
    
    private final static String QUEUE_NAME = "QUEUENOMI AYNAN QAYSI EKANLIGI";
    private final static String DirectExchange="MAXSUS YARATILGAN EXCHANCHE TYPE";



    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = getConnectionFactory();
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
```
##Uzatuvchi(Producer)

```java
public class Producer {
    
    private final static String FanoutExchange = "Fanout type uziz yaratgan";

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = getConnectionFactory();
        try (Connection connection = getConnectionFactory();
             Channel channel = connection.createChannel()) {
            int count = 0;
            do {
                String message = "Hello World From Java!---->" + count;
                channel.basicPublish(FanoutExchange, key, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                ++count;
                Thread.sleep(4_00);
            } while (true);

        } catch (IOException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```



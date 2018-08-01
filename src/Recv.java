import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private final static String QUEUE_NAME = "Junhee-queue";

    public static void main(String[] argv) throws Exception {

        // Setting up is the same as the publisher;
        // we open a connection and a channel, and declare the queue from which we're going to consume.
        // Note this matches up with the queue that send publishes to.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // We're about to tell the server to deliver us the messages from the queue.
        // Since it will push us messages asynchronously,
        // we provide a callback in the form of an object that will buffer the messages until we're ready to use them.
        // That is what a DefaultConsumer subclass does.
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
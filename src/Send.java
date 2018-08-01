import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "Junhee-queue";

    public static void main(String[] argv) throws Exception {

        // then we can create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // To send,
        // we must declare a queue for us to send to;
        // then we can publish a message to the queue:
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "this is sending message";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        // close the channel and the connection;
        channel.close();
        connection.close();
    }
}
package com.mariuszzareba.jmsproject;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SubscriberClient {
    public static void main(String[] args) throws JMSException {

        Connection connection = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:22222");
            connection = connectionFactory.createConnection();
            connection.setClientID("TestSubscriberClient");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("Example.Library.Publication");

            MessageConsumer consumer = session.createDurableSubscriber(topic, "consumer", "", false);
            // with durable subscription, publications are still cached when subscriber stopped running
            connection.start();

            TextMessage msg;
            msg = (TextMessage) consumer.receive();
            String msgText = msg.getText();
            System.out.println("Receiving text: \n" + msgText);

            session.close();            // after receiving one msg, close session with the broker
        } finally {
            if (connection != null) {
                connection.close();
            }

        }
    }
}

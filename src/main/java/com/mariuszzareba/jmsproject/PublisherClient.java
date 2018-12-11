package com.mariuszzareba.jmsproject;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class PublisherClient {
    public static void main(String[] args) throws JMSException {

        Connection connection = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:22222");
            connection = connectionFactory.createConnection();
            connection.setClientID("TestPublisherClient");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // synchronous, when msg is sent wait for confirmation from broker as AUTO_ACKNOWLEDGE is enabled
            // throws JMSException otherwise
            Topic topic = session.createTopic("Example.Library.Publication");

            try {
                TextFileAsString textfileasstring = new TextFileAsString();
                String s = textfileasstring.getTextFileAsString("exercise-1.xml");
                TextMessage msg = session.createTextMessage(s);
                MessageProducer publisher = session.createProducer(topic);
                // Topic object sent as arg means producer is operating as publisher

                System.out.println("Sending text: \n'" + s + "'");
                publisher.send(msg, DeliveryMode.PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
                //with DeliveryMode.PERSISTENT, messages survive broker's failure
                //throws JMSException if failed
                session.close();

            } catch (IOException e) {    // gets thrown if loaded file is not a text file or is corrupted
                System.out.println("Can't load file. Check file name and extension");
                e.printStackTrace();
                System.exit(2);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }
}

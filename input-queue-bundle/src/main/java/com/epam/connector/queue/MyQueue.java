package com.epam.connector.queue;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyQueue {
	
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	public MyQueue(String connectionFactoryName, String queueName) throws JMSException, NamingException {
		connection = ((ConnectionFactory) new InitialContext().lookup(connectionFactoryName))
			.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue(queueName);
	}
	
	public void sendMessage(String message) throws JMSException {
		if (producer == null) {
			producer = session.createProducer(queue);
		}
		producer.send(session.createTextMessage(message));
	}
	
	
	public String receiveMessage() throws JMSException {
		
		if (consumer == null) {
			consumer = session.createConsumer(queue);
		}
		String message = "";
		
		Message received = consumer.receive(1000);
		
		if (received != null) {
			message = ((TextMessage) received).getText();
		}
		
		return message;
	}
	
	public void setListener(MessageListener messageListener) throws JMSException {
		if (consumer == null) {
			consumer = session.createConsumer(queue);
		}
		System.out.println(consumer.getClass());
		
		consumer.setMessageListener(messageListener);
	}
	
	public void close() throws JMSException {
		connection.close();
		session.close();
		
		if (producer != null) {
			producer.close();
		}
		if (consumer != null) {
			consumer.close();
		}
	}
}

package com.epam.connector.queue;

import com.epam.calc.api.FactorialService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class InputQueueListener implements MessageListener {
	
	private MyQueue outputQueue;
	private FactorialService calculator;
	
	public InputQueueListener(MyQueue outputQueue, FactorialService calculator) {
		this.outputQueue = outputQueue;
		this.calculator = calculator;
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			String number = ((TextMessage) message).getText();
			String result = String.valueOf(calculator.calculateFactorial(Integer.valueOf(number)));
			outputQueue.sendMessage(result);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

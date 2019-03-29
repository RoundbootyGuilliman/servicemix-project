package com.epam.cxf.api;

import com.epam.connector.queue.MyQueue;

import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public class Endpoint {
	
	private MyQueue inputQueue;
	private MyQueue outputQueue;
	
	public Endpoint(MyQueue inputQueue, MyQueue outputQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
	}
	
	@GET
	@Path("/factorial")
	public String getFactorial(@QueryParam("number") String number) throws JMSException {
		
		inputQueue.sendMessage(number);
		return number + "! = " + outputQueue.receiveMessage();
	}
}

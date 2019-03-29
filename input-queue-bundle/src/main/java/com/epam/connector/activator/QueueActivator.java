package com.epam.connector.activator;

import com.epam.calc.api.FactorialService;
import com.epam.connector.queue.InputQueueListener;
import com.epam.connector.queue.MyQueue;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class QueueActivator implements BundleActivator {
	
	private static final String CONN_FACTORY_JNDI = "osgi:service/jms/default-cf";
	
	private MyQueue inputQueue;
	private MyQueue outputQueue;
	
	public void start(final BundleContext context) throws NamingException, JMSException {
		
		System.out.println("input-queue-bundle started");
		
		FactorialService calculator = context.getService(context.getServiceReference(FactorialService.class));
		
		inputQueue = new MyQueue(CONN_FACTORY_JNDI, "inputQueue");
		outputQueue = new MyQueue(CONN_FACTORY_JNDI, "outputQueue");
		
		inputQueue.setListener(new InputQueueListener(outputQueue, calculator));
	}
	
	public void stop(final BundleContext context) throws JMSException {
		inputQueue.close();
		outputQueue.close();
		System.out.println("input-connector-bundle stopped");
	}
}
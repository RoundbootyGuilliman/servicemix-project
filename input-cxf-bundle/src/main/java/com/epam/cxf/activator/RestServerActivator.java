package com.epam.cxf.activator;

import com.epam.cxf.api.Endpoint;
import com.epam.connector.queue.MyQueue;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class RestServerActivator implements BundleActivator {
	
	private static final String CONN_FACTORY_JNDI = "osgi:service/jms/default-cf";
	
	private MyQueue inputQueue;
	private MyQueue outputQueue;
	private Server server;
	private JAXRSServerFactoryBean factoryBean;
	
	public void start(final BundleContext context) throws NamingException, JMSException {
		createQueues();
		startServer();
		System.out.println("Server started");
	}
	
	public void stop(final BundleContext context) throws JMSException {
		inputQueue.close();
		outputQueue.close();
		stopServer();
		System.out.println("Server stopped");
	}
	
	private void createQueues() throws JMSException, NamingException {
		inputQueue = new MyQueue(CONN_FACTORY_JNDI, "inputQueue");
		outputQueue = new MyQueue(CONN_FACTORY_JNDI, "outputQueue");
	}
	
	private void startServer() {
		factoryBean = new JAXRSServerFactoryBean();
		factoryBean.setResourceClasses(Endpoint.class);
		factoryBean.setResourceProvider(new SingletonResourceProvider(new Endpoint(inputQueue, outputQueue)));
		factoryBean.setAddress("http://localhost:8080/");
		server = factoryBean.create();
		
	}
	
	private void stopServer() {
		if (server != null && server.isStarted()) {
			server.stop();
			server.destroy();
			factoryBean.getBus().shutdown(true);
		}
	}
}
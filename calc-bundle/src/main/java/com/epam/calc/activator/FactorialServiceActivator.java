package com.epam.calc.activator;

import com.epam.calc.api.FactorialService;
import com.epam.calc.service.FactorialServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FactorialServiceActivator implements BundleActivator {
	
	private ServiceRegistration serviceRegistration;
	
	public void start(final BundleContext context) {
		serviceRegistration = context.registerService(
			FactorialService.class.getName(), new FactorialServiceImpl(), null);
		System.out.println("calc-bundle started");
	}
	
	public void stop(final BundleContext context) {
		System.out.println("calc-bundle stopped");
		serviceRegistration.unregister();
	}
}
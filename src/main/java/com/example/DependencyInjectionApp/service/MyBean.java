package com.example.DependencyInjectionApp.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class MyBean implements InitializingBean, DisposableBean {

	private String resourceName;

	// Constructor
	public MyBean() {
		this.resourceName = "DefaultResource";
		System.out.println("1. Bean is being instantiated - Constructor called");
	}

	// Setter for dependency injection if needed
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
		System.out.println("2. Dependency injected - Resource name set to: " + resourceName);
	}

	// Overridden from InitializingBean, called after dependencies are injected
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("3. afterPropertiesSet() - Bean properties are set and ready");
		System.out.println("   Resource initialized with name: " + resourceName);
	}

	// Custom initialization method using @PostConstruct
	@PostConstruct
	public void init() {
		System.out.println("4. @PostConstruct - Custom initialization tasks");
		System.out.println("   Connecting to resource: " + resourceName);
		// Simulate connection setup
	}

	// Business method
	public void performTask() {
		System.out.println("5. Business logic - Performing task using " + resourceName);
	}

	// Custom destroy method using @PreDestroy
	@PreDestroy
	public void preDestroy() {
		System.out.println("6. @PreDestroy - Custom destruction tasks");
		System.out.println("   Disconnecting from resource: " + resourceName);
		// Simulate resource cleanup
	}

	// Overridden from DisposableBean, called when the bean is destroyed
	@Override
	public void destroy() throws Exception {
		System.out.println("7. destroy() - Final cleanup before bean is destroyed");
	}
}

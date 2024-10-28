package com.example.DependencyInjectionApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.DependencyInjectionApp.controller.MyController;;

@SpringBootApplication
public class DIApp {

	public static void main(String[] args) {

		runDepencencyInjectionExample(args);
	}

	public static void runDepencencyInjectionExample(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DIApp.class, args);

		MyController myContoller = ctx.getBean(MyController.class);

		System.out.println("In main method " + myContoller.sayHello());

	}
}
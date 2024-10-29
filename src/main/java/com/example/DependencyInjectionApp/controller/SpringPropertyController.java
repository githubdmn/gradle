package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.SpringGreeting;

@Controller
public class SpringPropertyController {

	@Autowired // not recommended
	private SpringGreeting service;

	public String sayHello() {
		return "SPRING property injected controller " + this.service.sayGreeting();
	}
}

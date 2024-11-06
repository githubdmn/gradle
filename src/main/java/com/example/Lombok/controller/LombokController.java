package com.example.Lombok.controller;

import org.springframework.stereotype.Controller;

@Controller
public class LombokController {

	public String sayHello() {
		return "Hello, World! LOMBOK";
	}
}

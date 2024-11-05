package com.example.DependencyInjectionApp.service.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.DependencyInjectionApp.service.GreetingService;

@Profile({ "EN", "default" })
@Service("i18nService")
public class EnglishGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
		return "\n\n 1. English Greeting Service IS DEFAULT profile \n";
	}

}

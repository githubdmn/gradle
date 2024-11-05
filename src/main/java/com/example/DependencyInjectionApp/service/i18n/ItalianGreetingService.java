package com.example.DependencyInjectionApp.service.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.DependencyInjectionApp.service.GreetingService;

@Profile("IT")
@Service("i18nService")
public class ItalianGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
		return "\n\n Ciao! Benvenuto!\n\n";
	}
}

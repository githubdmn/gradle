package com.example.DependencyInjectionApp.service.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.DependencyInjectionApp.service.GreetingService;

@Profile("ES")
@Service("i18nService")
public class SpanishGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
		return "¡Hola Mundo! - Servicio de Saludo en Español";
	}

}

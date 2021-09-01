package br.com.esparda.privilegioacesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.esparda.commons.StatusService;
import br.com.esparda.commons.configuration.JMSConfig;
import br.com.esparda.commons.configuration.MethodSecurityConfig;
import br.com.esparda.commons.configuration.OAuth2ResourceConfig;
import br.com.esparda.commons.evento.EventoService;

@SpringBootApplication
@Import({ MethodSecurityConfig.class, OAuth2ResourceConfig.class, JMSConfig.class, EventoService.class,
	StatusService.class })
public class PrivilegioAcessoApp {

	public static void main(String[] args) {
		SpringApplication.run(PrivilegioAcessoApp.class, args);

	}

}
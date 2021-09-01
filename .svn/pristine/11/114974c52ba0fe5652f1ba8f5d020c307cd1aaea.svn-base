package br.com.esparda.monitor;

import java.util.UUID;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import br.com.esparda.commons.SpringUtils;
import br.com.esparda.commons.configuration.JMSErrorHandler;
import br.com.esparda.commons.configuration.MethodSecurityConfig;
import br.com.esparda.commons.configuration.OAuth2ResourceConfig;

@SpringBootApplication
@Import({ MethodSecurityConfig.class, OAuth2ResourceConfig.class, SpringUtils.class })
public class MonitorApp {

	@Bean
	JmsListenerContainerFactory<?> espardaJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setSubscriptionDurable(true);
		factory.setPubSubDomain(true);
		factory.setClientId("monitor-service-" + UUID.randomUUID());
		factory.setErrorHandler(new JMSErrorHandler());
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(MonitorApp.class, args);

	}

}
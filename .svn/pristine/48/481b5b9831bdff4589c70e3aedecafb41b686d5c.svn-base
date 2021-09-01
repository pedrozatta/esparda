package br.com.esparda.commons.configuration;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

public class JMSConfig {
	
	@Bean
	JmsListenerContainerFactory<?> espardaJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}
}


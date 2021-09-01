package br.com.esparda.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.esparda.commons.evento.EventoService;

@EnableScheduling
@Component
public class StatusService {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private Environment env;

	private static boolean needToRunStartupMethod = true;

	@Scheduled(fixedRate = 10000)
	public void timeout() {

		if (needToRunStartupMethod) {
			eventoService.sendStatusEvent(env.getProperty("br.com.esparda.info"), null);
			needToRunStartupMethod = false;
			return;
		}
		eventoService.sendStatusEvent(env.getProperty("br.com.esparda.info"), null);
	}

}
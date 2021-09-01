package br.com.esparda.commons.configuration;

import java.util.logging.Logger;

import org.springframework.util.ErrorHandler;

import br.com.esparda.commons.LogUtil;

public class JMSErrorHandler implements ErrorHandler {
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Override
    public void handleError(Throwable t) {
    	t.printStackTrace();
		LogUtil.debug(LOGGER, "handleError");
    }
}
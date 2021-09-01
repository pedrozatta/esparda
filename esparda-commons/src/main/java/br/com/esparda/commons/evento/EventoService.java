package br.com.esparda.commons.evento;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.esparda.commons.JsonUtil;
import br.com.esparda.commons.LogUtil;
import br.com.esparda.commons.MensagemUtil;
import br.com.esparda.commons.domain.IEntidade;

@Component
public class EventoService {

	private static final Logger LOGGER = Logger.getLogger(EventoService.class.getName());

	@Autowired
	public JmsTemplate jmsTemplate;
	
	public void sendToStore(IEntidade<?> entidade, boolean excluir) {
		System.out.println("sendToStore(..)");
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				// System.out.println(auth.getPrincipal());

				ObjectMessage message = session.createObjectMessage();

				if (excluir) {
					message.setJMSType(entidade.getClass().getSimpleName() + ":excluir");
				} else {
					message.setJMSType(entidade.getClass().getSimpleName() + ":persistir");
				}
				message.setStringProperty(MensagemUtil.USUARIO_PROPERTY, name);
				message.setStringProperty(MensagemUtil.TIPO_PROPERTY, entidade.getClass().getSimpleName());
				if (excluir) {
					message.setBooleanProperty(MensagemUtil.EXCLUIR_PROPERTY, excluir);
				}
				// apenas informativo
				message.setStringProperty(MensagemUtil.ORIGEM_CTRL_PROPERTY, this.getClass().getName());
				message.setStringProperty(MensagemUtil.ORIGEM_TIPO_PROPERTY, entidade.getClass().getName());
				message.setObject(JsonUtil.toJson(entidade));
				return message;
			}
		};
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.send(MensagemUtil.STORAGE_DESTINATION, messageCreator);

	}

	public void sendEvent(IEntidade<?> entidade, String event, Map<String, String> props) {
		System.out.println("sendToStore(..)");
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				// System.out.println(auth.getPrincipal());

				ObjectMessage message = session.createObjectMessage();

				message.setJMSType(entidade.getClass().getSimpleName() + ":" + event);

				message.setStringProperty(MensagemUtil.USUARIO_PROPERTY, name);
				message.setStringProperty(MensagemUtil.TIPO_PROPERTY, entidade.getClass().getSimpleName());
				message.setStringProperty(MensagemUtil.EVENT_PROPERTY, event);

				// dado apenas informativo
				message.setStringProperty(MensagemUtil.ORIGEM_CTRL_PROPERTY, this.getClass().getName());
				message.setStringProperty(MensagemUtil.ORIGEM_TIPO_PROPERTY, entidade.getClass().getName());

				if (props != null) {
					for (Entry<String, String> prop : props.entrySet()) {
						message.setStringProperty(prop.getKey(), prop.getValue());
					}
				}
				message.setObject(JsonUtil.toJson(entidade));
				return message;
			}
		};
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.send(MensagemUtil.EVENT_DESTINATION, messageCreator);

	}

	public void sendStatusEvent(String servico, Map<String, String> props) {
		LogUtil.debug(LOGGER, "sendStatusEvent(..) servico: {0}", servico);

		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {

				Message message = session.createMessage();

				message.setJMSType(servico);
				message.setStringProperty("DATA", Calendar.getInstance().getTime().toString());
				
				if (props != null) {
					for (Entry<String, String> prop : props.entrySet()) {
						message.setStringProperty(prop.getKey(), prop.getValue());
					}
				}
				return message;
			}
		};
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.send(MensagemUtil.EVENT_DESTINATION, messageCreator);

	}

}

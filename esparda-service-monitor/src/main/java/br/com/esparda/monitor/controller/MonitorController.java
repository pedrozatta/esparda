package br.com.esparda.monitor.controller;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.esparda.commons.AbstractController;
import br.com.esparda.commons.JsonUtil;
import br.com.esparda.commons.ListUtil;
import br.com.esparda.commons.LogUtil;
import br.com.esparda.commons.MensagemUtil;
import br.com.esparda.commons.Util;
import br.com.esparda.commons.domain.IEntidade;
import br.com.esparda.commons.meta.AgregacaoAnnotation;
import br.com.esparda.commons.meta.Cardinalidade;
import br.com.esparda.commons.meta.ComposicaoAnnotation;
import br.com.esparda.monitor.domain.MonitorService;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementCopy;
import tools.devnull.trugger.element.Elements;
import tools.devnull.trugger.reflection.Reflection;

@RestController
public class MonitorController extends AbstractController {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	@Autowired
	public JmsTemplate jmsTemplate;

	@Autowired
	public EntityManager em;

	private Map<String, MonitorService> cache;

	public MonitorController() {
		cache = new HashMap<String, MonitorService>();
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Collection<MonitorService> finAll() {
		LogUtil.debug(LOGGER, "finAll()");

		for (MonitorService monitorService : cache.values()) {
			if (monitorService.getUltima() != null) {
				long diferenca = Calendar.getInstance().getTimeInMillis() - monitorService.getUltima().getTime();
				if (diferenca > 15 * 1000) {
					monitorService.setStatus(MonitorService.STATUS_INATIVO);
				}
			}

		}

		return cache.values();
	}

	@RequestMapping(value = "/clean", method = RequestMethod.GET)
	public Collection<MonitorService> clean() {
		LogUtil.debug(LOGGER, "clean()");

		for (MonitorService monitorService : cache.values()) {
			monitorService.setQuantidade(0);
			monitorService.setPrimeira(null);
			monitorService.setUltima(null);
			monitorService.setStatus(MonitorService.STATUS_PENDENTE);

		}

		return cache.values();
	}

	@Transactional
	@JmsListener(destination = MensagemUtil.EVENT_DESTINATION, containerFactory = "espardaJmsContainerFactory", concurrency = "1")
	public void receiveMessage(Message message) throws JMSException {
		LogUtil.debug(LOGGER, "receiveMessage {0}", message.getJMSType());

		MonitorService monitorService = cache.get(message.getJMSType());
		if (monitorService == null) {
			monitorService = new MonitorService(message.getJMSType(), new Date(message.getJMSTimestamp()));
			cache.put(message.getJMSType(), monitorService);
		}

		if (monitorService.getUltima() == null) {
			monitorService.setUltima(new Date(message.getJMSTimestamp()));
		}

		long diferenca = message.getJMSTimestamp() - monitorService.getUltima().getTime();
		if (diferenca < 20 * 1000) {
			monitorService.setStatus(MonitorService.STATUS_ATIVO);
		}

		monitorService.setUltima(new Date(message.getJMSTimestamp()));
		monitorService.setQuantidade(monitorService.getQuantidade() + 1);

	}

	// @Transactional
	// @JmsListener(destination = MensagemUtil.EVENT_DESTINATION,
	// containerFactory = "espardaJmsContainerFactory")
	// public void receiveMessage(MapMessage message) throws JMSException {
	// LogUtil.debug(LOGGER, "receiveMessage");
	// }
	//
	// @Transactional
	// @JmsListener(destination = MensagemUtil.EVENT_DESTINATION,
	// containerFactory = "espardaJmsContainerFactory")
	// public void receiveMessage(BytesMessage message) throws JMSException {
	// LogUtil.debug(LOGGER, "receiveMessage");
	// }
	//
	// @JmsListener(destination = MensagemUtil.EVENT_DESTINATION,
	// containerFactory = "espardaJmsContainerFactory")
	// public void receiveMessage(ObjectMessage message) throws JMSException {
	// LogUtil.debug(LOGGER, "receiveMessage");
	// }

	public void processa(ObjectMessage message, Class<?> entidadeClass) throws JMSException {

		LogUtil.debug("processa(..) " + message.getJMSType());

		boolean excluir = message.getBooleanProperty(MensagemUtil.EXCLUIR_PROPERTY);

		String json = (String) message.getObject();

		Gson gson = JsonUtil.createGson();
		Object item = gson.fromJson(json, entidadeClass);

		if (excluir) {
			this.remove((IEntidade<?>) item);
		} else {
			this.save((IEntidade<?>) item);
		}

		System.out.println();
	}

	private void remove(IEntidade<?> item) {
		IEntidade<?> entidade = em.find(item.getClass(), item.getChave());
		if (entidade != null) {
			em.remove(entidade);
		}
	}

	private void save(IEntidade<?> item) {
		IEntidade<?> entidade = em.find(item.getClass(), item.getChave());
		if (entidade == null) {
			LogUtil.debug("Nova entidade");
			this.incluir(item);
			return;
		} else {
			this.update(entidade, item);
		}
	}

	public void incluir(IEntidade<?> entidade) {

		processaAssociacoes(entidade, null);

		em.persist(entidade);

	}

	@SuppressWarnings("all")
	public void update(IEntidade<?> persistente, IEntidade<?> nova) {
		// TODO: verificar necessidade de cache, talvez nao seja necessário
		if (persistente == null || nova == null) {
			throw new RuntimeException("não é possivel atualizar");
		}
		if (!persistente.getClass().equals(nova.getClass())) {
			throw new RuntimeException("entidades imcompativeis");
		}
		if (!persistente.equals(nova)) {
			throw new RuntimeException("entidades imcompativeis");
		}
		List<Element> elements = Elements.elements().in(persistente);
		for (Element persistenteElement : elements) {
			if (!persistenteElement.isReadable() || !persistenteElement.isWritable()) {
				continue;
			}
			// if
			// (persistenteElement.name().equals(EntidadeUtil.ATIRUBTO_CHAVE)) {
			// continue;
			// }
			if (persistenteElement.isAnnotationPresent(ComposicaoAnnotation.class)) {
				ComposicaoAnnotation annot = persistenteElement.getAnnotation(ComposicaoAnnotation.class);
				if (annot.cardinalidade() == Cardinalidade.UM) {
					System.out.println();
				} else {
					List persistenteList = (List) persistenteElement.value();
					List novaList = (List) Elements.element(persistenteElement.name()).in(nova).value();

					ListUtil listUtil = new ListUtil<>();
					listUtil.setAntiga(persistenteList);
					listUtil.setNova(novaList);
					listUtil.refresh();

					if (persistenteList == null) {
						Field field = Reflection.field(persistenteElement.name()).in(persistente);

						persistenteElement.set(Util.newInstance(field.getType()));
						persistenteList = (List) persistenteElement.value();

					}
					persistenteList.removeAll(listUtil.getRemovidos());

					persistenteList.addAll(listUtil.getAdicionados());

					for (Object item : listUtil.getIntersecao().entrySet()) {
						Map.Entry entry = (Map.Entry) item;
						update((IEntidade) entry.getKey(), (IEntidade) entry.getValue());
					}

				}

			} else if (persistenteElement.isAnnotationPresent(AgregacaoAnnotation.class)) {
				AgregacaoAnnotation annot = persistenteElement.getAnnotation(AgregacaoAnnotation.class);
				if (annot.cardinalidade() == Cardinalidade.UM) {
					System.out.println();
				} else {
					System.out.println();
				}

			} else {
				Element novaElement = Elements.element(persistenteElement.name()).in(nova);
				persistenteElement.set(novaElement.value());
			}
		}
		System.out.println();
	}

	public void update1(IEntidade<?> persistente, IEntidade<?> nova) {
		Elements.copy(Elements.elements().filter(element -> updateFilter(element))).from(nova)
				.applying(copy -> updateApplying(copy)).to(persistente);

	}

	private boolean updateFilter(Element element) {
		if (element.isAnnotationPresent(Id.class)) {
			return false;
		}
		return true;
	}

	private Object updateApplying(ElementCopy copy) {
		if (!(copy.value() instanceof List)) {
			return copy.value();
		}
		try {
			List<?> aux = (List<?>) copy.value().getClass().newInstance();

			// for(Object o : (List)copy.value()){
			//
			// this.update(persistente, nova);
			// }
			return aux;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

}
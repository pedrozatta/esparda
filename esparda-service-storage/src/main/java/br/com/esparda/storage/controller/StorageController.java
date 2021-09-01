package br.com.esparda.storage.controller;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.esparda.commons.AbstractController;
import br.com.esparda.commons.EntidadeUtil;
import br.com.esparda.commons.JsonUtil;
import br.com.esparda.commons.ListUtil;
import br.com.esparda.commons.LogUtil;
import br.com.esparda.commons.MensagemUtil;
import br.com.esparda.commons.Util;
import br.com.esparda.commons.domain.IEntidade;
import br.com.esparda.commons.meta.AgregacaoAnnotation;
import br.com.esparda.commons.meta.Cardinalidade;
import br.com.esparda.commons.meta.ComposicaoAnnotation;
import br.com.esparda.storage.domain.PrivilegioAcesso;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementCopy;
import tools.devnull.trugger.element.Elements;
import tools.devnull.trugger.reflection.Reflection;

@RestController
public class StorageController extends AbstractController {

	private Log log = LogFactory.getLog(StorageController.class);

	@Autowired
	public JmsTemplate jmsTemplate;

	@Autowired
	public EntityManager em;

	@RequestMapping(value = "/{entidade}/count", method = RequestMethod.GET)
	public Long findById(@PathVariable String entidade) {
		log.debug("log test");

		Class<?> clazz = EntidadeUtil.getClass(entidade);
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(clazz)));
		// cq.where(/* your stuff */);
		return em.createQuery(cq).getSingleResult();
	}

	@RequestMapping(value = "/{entidade}/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') and #oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_ADMIN'))")
	public Object findById(@PathVariable String entidade, @PathVariable Long id) {

		Class<?> clazz = EntidadeUtil.getClass(entidade);

		return em.find(clazz, id);
	}

	protected void sendMessage(Object object) {
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setStringProperty("user", "p-pzatta");
				message.setStringProperty("entidade", PrivilegioAcesso.class.getName());
				message.setObject(JsonUtil.toJson(object));
				return message;
			}
		};
		System.out.println("sendMessage(..)");
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.send(MensagemUtil.STORAGE_DESTINATION, messageCreator);

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@JmsListener(destination = MensagemUtil.STORAGE_DESTINATION, containerFactory = "espardaJmsContainerFactory")
	public void receiveMessage(Message message) throws JMSException {

		System.out.println();

		Class<IEntidade<?>> entidadeClass = EntidadeUtil
				.getClass(message.getStringProperty(MensagemUtil.TIPO_PROPERTY));

		if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage) message;
			for (String name : Collections.list((Enumeration<String>) mapMessage.getMapNames())) {
				System.out.println(name + " " + mapMessage.getString(name));
			}
		} else if (message instanceof ObjectMessage) {

			this.processa((ObjectMessage) message, entidadeClass);

		} else {
			System.out.println("formato não suportado");
		}
	}

	public void processa(ObjectMessage message, Class<?> entidadeClass) throws JMSException {

		LogUtil.debug("processa(..) " + message.getJMSType());

		boolean excluir = message.getBooleanProperty(MensagemUtil.EXCLUIR_PROPERTY);

		String json = (String) message.getObject();

		Gson gson = JsonUtil.createGson();
		Object item = gson.fromJson(json, entidadeClass);

		// TODO: REMOVER *****
		// if (item instanceof Usuario) {
		// UsuarioPrivilegio privilegioUsuario = new UsuarioPrivilegio();
		// privilegioUsuario.setChave(1L);
		// privilegioUsuario.setInicio(Calendar.getInstance().getTime());
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// privilegioUsuario.setFim(Calendar.getInstance().getTime());
		//
		// Usuario usuario = (Usuario) item;
		// usuario.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		// usuario.getPrivilegios().add(privilegioUsuario);
		// }

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
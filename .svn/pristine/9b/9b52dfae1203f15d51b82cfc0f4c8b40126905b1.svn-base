package br.com.esparda.commons;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.esparda.commons.domain.IEntidade;
import br.com.esparda.commons.meta.AgregacaoAnnotation;
import br.com.esparda.commons.meta.Cardinalidade;
import br.com.esparda.commons.meta.ComposicaoAnnotation;
import br.com.esparda.commons.vo.ServicoItemVO;
import br.com.esparda.commons.vo.ServicoVO;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementPredicates;
import tools.devnull.trugger.element.Elements;
import tools.devnull.trugger.reflection.Reflection;

public abstract class AbstractController {

	@PersistenceContext
	public EntityManager em;

	@Autowired
	protected Environment env;

	@Value("${server.port}")
	protected String porta;

	@RequestMapping("/info")
	public ServicoVO info() {

		ServicoVO servico = new ServicoVO();
		servico.setNome(env.getProperty("br.com.esparda.info"));
		servico.setImplementacao(this.getClass().getName());
		servico.setEsparda(env.getProperty("br.com.esparda.nome"));
		servico.setServidor(Util.getServerName() + ":" + this.porta);
		processaMetodos(servico, this.getClass());

		return servico;
	}

	private void processaMetodos(ServicoVO servico, Class<?> clazz) {

		RequestMapping annotationAux = this.getClass().getAnnotation(RequestMapping.class);
		String url = env.getProperty("server.contextPath");
		if (annotationAux != null && annotationAux.value() != null && annotationAux.value().length > 0) {
			url += annotationAux.value()[0];
		}

		servico.setItens(new ArrayList<ServicoItemVO>());
		List<Method> methods = Reflection.reflect().methods().deep()
				.filter(method -> method.isAnnotationPresent(RequestMapping.class)).in(this);

		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {

				if (annotation instanceof RequestMapping) {
					ServicoItemVO item = new ServicoItemVO();
					servico.getItens().add(item);
					RequestMapping requestMappingAnnotation = (RequestMapping) annotation;
					item.setNome(method.getName());
					if (Util.isNullOrEmpty(requestMappingAnnotation.method())) {
						item.setMetodo(RequestMethod.GET.name());
					} else {
						item.setMetodo(requestMappingAnnotation.method()[0].name());
					}
					if (Util.isNullOrEmpty(requestMappingAnnotation.value())) {
						item.setUrl(url);
					} else {
						item.setUrl(url + requestMappingAnnotation.value()[0]);
					}
				}
			}
		}
	}

	@RequestMapping("/status")
	public String status() {
		return "{\"status\":\"Ativo!\"}";
	}

	/**
	 * Processa asassociacoes recrusivamente
	 * 
	 * @param elements
	 */

	// TODO: armazenar entidades processadas no cache
	protected void processaAssociacoes(IEntidade<?> entity, Map<Object, Object> cache) {
		if (cache == null) {
			cache = new HashMap<Object, Object>();
		}
		List<Element> composicaoElements = Elements.elements()
				.filter(ElementPredicates.annotatedWith(ComposicaoAnnotation.class)).in(entity);
		processaComposicoes(composicaoElements, cache);

		List<Element> agregacaoElements = Elements.elements()
				.filter(ElementPredicates.annotatedWith(AgregacaoAnnotation.class)).in(entity);
		processaAgregacoes(agregacaoElements, cache);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void processaComposicoes(List<Element> elements, Map<Object, Object> cache) {
		if (Util.isNullOrEmpty(elements)) {
			return;
		}
		for (Element element : elements) {
			if (!element.isReadable() || !element.isWritable()) {
				continue;
			}
			ComposicaoAnnotation annotation = element.getAnnotation(ComposicaoAnnotation.class);
			if (annotation.cardinalidade() == Cardinalidade.UM) {
				IEntidade<?> entidade = processaComposicoesItem((IEntidade<?>) element.value(), cache);
				element.set(entidade);
			} else {
				if (element.value() != null) {
					List lista = (List) Util.newInstance(element.type());
					if (element.value() != null) {
						for (IEntidade entidade : (List<IEntidade>) element.value()) {
							IEntidade<?> entidadeAux = processaComposicoesItem(entidade, cache);
							lista.add(entidadeAux);
						}
					}
					element.set(lista);
				}
			}
		}

	}

	// TODO RENOMEAR METODO PARA processaComposicaoItem
	protected IEntidade<?> processaComposicoesItem(IEntidade<?> item, Map<Object, Object> cache) {
		if (item == null) {
			return null;
		}
		this.processaAssociacoes(item, cache);
		if (Util.isNullOrEmpty(item.getChave())) {
			em.persist(item);
		} else {
			em.merge(item);
		}
		return item;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void processaAgregacoes(List<Element> elements, Map<Object, Object> cache) {
		if (Util.isNullOrEmpty(elements)) {
			return;
		}
		for (Element element : elements) {
			if (!element.isReadable() || !element.isWritable()) {
				continue;
			}
			AgregacaoAnnotation annotation = element.getAnnotation(AgregacaoAnnotation.class);
			if (annotation.cardinalidade() == Cardinalidade.UM) {
				IEntidade<?> entidade = processaAgregacaoItem((IEntidade<?>) element.value(), cache);
				element.set(entidade);
			} else {
				List lista = (List) Util.newInstance(element.type());
				for (IEntidade entidade : (List<IEntidade>) element.value()) {
					IEntidade<?> entidadeAux = processaAgregacaoItem(entidade, cache);
					lista.add(entidadeAux);
				}
				element.set(lista);
			}
		}
	}

	protected IEntidade<?> processaAgregacaoItem(IEntidade<?> item, Map<Object, Object> cache) {
		if (item == null) {
			return null;
		}
		IEntidade<?> entidadePersistente = em.find(item.getClass(), item.getChave());
		if (!Util.isNullOrEmpty(entidadePersistente)) {
			return entidadePersistente;
		}
		em.persist(item);
		return item;
	}

}

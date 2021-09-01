package br.com.esparda.commons;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.env.Environment;

import br.com.esparda.commons.meta.ComposicaoAnnotation;
import br.com.esparda.commons.vo.ServicoVO;
import br.com.esparda.mock.UsuarioAgregacaoImpl;
import br.com.esparda.mock.UsuarioImpl;
import br.com.esparda.mock.UsuarioPrivilegioImpl;
import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.impl.PopulatorBuilder;
import tools.devnull.trugger.element.Element;
import tools.devnull.trugger.element.ElementPredicates;
import tools.devnull.trugger.element.Elements;

public class AbstractControllerTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	Populator populator = new PopulatorBuilder().build();

	@Mock
	private Environment env;

	@Mock
	private Entidade<Long> entidade;

	@Mock
	public EntityManager em;

	@Spy
	private AbstractController abstractController;

	Map<Object, Object> cache;

	@Before
	public void setUp() throws Exception {
		cache = new HashMap<Object, Object>();

		abstractController.em = em;
		abstractController.env = env;
		abstractController.porta = "50000";
		Mockito.when(env.getProperty("br.com.esparda.info")).thenReturn("Esparda: Microservice Categoria V1");
		Mockito.when(env.getProperty("br.com.esparda.nome")).thenReturn("Esparda2");

	}

	@Test
	public void testInfo() {
		ServicoVO result = abstractController.info();
		Assert.assertNotNull(result);

		Assert.assertEquals(2, result.getItens().size());

		Assert.assertEquals("Esparda: Microservice Categoria V1", result.getNome());
		Assert.assertEquals("Esparda2", result.getEsparda());

		Assert.assertNotNull(result.getImplementacao());
		Assert.assertEquals(abstractController.getClass().getName(), result.getImplementacao());

		Assert.assertNotNull(result.getServidor());
		Assert.assertTrue(result.getServidor().endsWith(abstractController.porta));
	}

	@Test
	public void testStatus() {
		String result = abstractController.status();
		Assert.assertNotNull(result);
		Assert.assertTrue(result.contains("status"));
		Assert.assertTrue(result.contains("Ativo"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testProcessaAssociacoes() {
		abstractController.processaAssociacoes(entidade, null);
		Mockito.verify(abstractController).processaComposicoes(Mockito.anyList(), Mockito.anyMap());
		Mockito.verify(abstractController).processaAgregacoes(Mockito.anyList(), Mockito.anyMap());
	}

	@Test
	public void testProcessaComposicoes1() {
		abstractController.processaComposicoes(null, null);
	}

	@Test
	public void testProcessaComposicoes2() {
		UsuarioImpl usuario = new UsuarioImpl();

		List<Element> composicaoElements = Elements.elements()
				.filter(ElementPredicates.annotatedWith(ComposicaoAnnotation.class)).in(usuario);

		abstractController.processaComposicoes(composicaoElements, cache);

		Mockito.verify(abstractController).processaComposicoesItem(Mockito.any(), Mockito.any());
	}

	@Test
	public void testProcessaComposicoes3() {
		UsuarioImpl usuario = new UsuarioImpl();
		usuario.setPrivilegios(populator.populateBeans(UsuarioPrivilegioImpl.class, 5));

		List<Element> composicaoElements = Elements.elements()
				.filter(ElementPredicates.annotatedWith(ComposicaoAnnotation.class)).in(usuario);

		abstractController.processaComposicoes(composicaoElements, cache);

		Mockito.verify(abstractController, Mockito.atLeast(6)).processaComposicoesItem(Mockito.any(), Mockito.any());
	}

	@Test
	public void testProcessaComposicoesItem1() {
		abstractController.processaComposicoesItem(null, cache);

		Mockito.verify(em, Mockito.never()).merge(Mockito.any());
		Mockito.verify(em, Mockito.never()).persist(Mockito.any());
	}

	@Test
	public void testProcessaComposicoesItem2() {
		UsuarioImpl privilegio = new UsuarioImpl();
		abstractController.processaComposicoesItem(privilegio, cache);

		Mockito.verify(em).persist(Mockito.any());
		Mockito.verify(em, Mockito.never()).merge(Mockito.any());
	}

	@Test
	public void testProcessaComposicoesItem3() {
		UsuarioImpl privilegio = new UsuarioImpl();
		privilegio.setChave(123L);
		abstractController.processaComposicoesItem(privilegio, cache);

		Mockito.verify(em).merge(Mockito.any());
		Mockito.verify(em, Mockito.never()).persist(Mockito.any());
	}

	@Test
	public void testProcessaAgregacoes() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessaAgregacaoItem1() {
		abstractController.processaAgregacaoItem(null, cache);

		Mockito.verify(em, Mockito.never()).merge(Mockito.any());
		Mockito.verify(em, Mockito.never()).persist(Mockito.any());
	}

	@Test
	public void testProcessaAgregacaoItem2() {
		UsuarioAgregacaoImpl privilegio = new UsuarioAgregacaoImpl();
		abstractController.processaAgregacaoItem(privilegio, cache);

		Mockito.verify(em).persist(Mockito.any());
		Mockito.verify(em, Mockito.never()).merge(Mockito.any());
	}

	@Test
	public void testProcessaAgregacaoItem3() {
		UsuarioAgregacaoImpl privilegio = new UsuarioAgregacaoImpl();
		privilegio.setChave(123L);

		Mockito.when(em.find(UsuarioAgregacaoImpl.class, privilegio.getChave())).thenReturn(privilegio);

		abstractController.processaAgregacaoItem(privilegio, cache);

		Mockito.verify(em).find(privilegio.getClass(), privilegio.getChave());
		Mockito.verify(em, Mockito.never()).persist(Mockito.any());
		Mockito.verify(em, Mockito.never()).merge(Mockito.any());
	}

}

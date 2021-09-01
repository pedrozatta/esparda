package br.com.esparda.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.esparda.storage.controller.StorageController;
import br.com.esparda.storage.domain.PrivilegioAcesso;
import br.com.esparda.storage.domain.Usuario;
import br.com.esparda.storage.domain.UsuarioPrivilegio;

public class StorageControllerTest {

	private Usuario persistenteRef;
	private Usuario novaRef;
	private UsuarioPrivilegio usuarioPrivilegio1Ref;
	private UsuarioPrivilegio usuarioPrivilegio2Ref;
	private PrivilegioAcesso privilegioAcessoRef;
	private StorageController controller;

	@Before
	public void setUpBeforeClass() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		persistenteRef = new Usuario();
		persistenteRef.setChave(1L);
		persistenteRef.setNome("Nome 1");

		novaRef = new Usuario();
		novaRef.setChave(2L);
		novaRef.setNome("Nome 2");

		privilegioAcessoRef = new PrivilegioAcesso();
		privilegioAcessoRef.setChave(1L);
		privilegioAcessoRef.setNome("PrivilegioAcesso 1");

		usuarioPrivilegio1Ref = new UsuarioPrivilegio();
		usuarioPrivilegio1Ref.setChave(1L);
		usuarioPrivilegio1Ref.setInicio(format.parse("20150101"));
		usuarioPrivilegio1Ref.setFim(format.parse("20151231"));
		usuarioPrivilegio1Ref.setPrivilegioAcesso(privilegioAcessoRef);

		usuarioPrivilegio2Ref = new UsuarioPrivilegio();
		usuarioPrivilegio2Ref.setChave(2L);
		usuarioPrivilegio2Ref.setInicio(format.parse("20160101"));
		usuarioPrivilegio2Ref.setFim(format.parse("20161231"));
		usuarioPrivilegio2Ref.setPrivilegioAcesso(privilegioAcessoRef);

		controller = new StorageController();
	}

	@Test(expected = RuntimeException.class)
	public void updateImcompativel1() {
		controller.update(null, null);
	}

	@Test(expected = RuntimeException.class)
	public void updateImcompativel2() {
		controller.update(null, usuarioPrivilegio1Ref);
	}

	@Test(expected = RuntimeException.class)
	public void updateImcompativel3() {
		controller.update(usuarioPrivilegio1Ref, null);
	}

	@Test(expected = RuntimeException.class)
	public void updateImcompativel4() {
		controller.update(usuarioPrivilegio1Ref, persistenteRef);
	}

	@Test
	public void updateBasico() {

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome("Nome 1 Novo");

		controller.update(persistente, nova);

		Assert.assertEquals(persistenteRef.getChave(), persistente.getChave());

		Assert.assertEquals(persistente.getNome(), nova.getNome());

	}

	@Test
	public void updateComposicao1() {

		UsuarioPrivilegio usuarioPrivilegio = new UsuarioPrivilegio();
		usuarioPrivilegio.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio.setFim(usuarioPrivilegio1Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());
		persistente.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio);

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());

		controller.update(persistente, nova);

		Assert.assertEquals(0, persistente.getPrivilegios() == null ? 0 : persistente.getPrivilegios().size());

	}

	@Test
	public void updateComposicao2() {

		UsuarioPrivilegio usuarioPrivilegio = new UsuarioPrivilegio();
		usuarioPrivilegio.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio.setFim(usuarioPrivilegio1Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());
		nova.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		nova.getPrivilegios().add(usuarioPrivilegio);

		controller.update(persistente, nova);

		Assert.assertNotNull(persistente.getPrivilegios());
		Assert.assertEquals(persistente.getPrivilegios().size(), 1);

	}

	@Test
	public void updateComposicao3() {

		UsuarioPrivilegio usuarioPrivilegio1 = new UsuarioPrivilegio();
		usuarioPrivilegio1.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio1.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio1.setFim(usuarioPrivilegio1Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2 = new UsuarioPrivilegio();
		usuarioPrivilegio2.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2.setFim(usuarioPrivilegio2Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());
		persistente.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio1);

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());
		nova.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		nova.getPrivilegios().add(usuarioPrivilegio2);

		controller.update(persistente, nova);

		Assert.assertNotNull(persistente.getPrivilegios());
		Assert.assertEquals(persistente.getPrivilegios().size(), 1);

	}

	@Test
	public void updateComposicao4() {

		UsuarioPrivilegio usuarioPrivilegio1 = new UsuarioPrivilegio();
		usuarioPrivilegio1.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio1.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio1.setFim(usuarioPrivilegio1Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2 = new UsuarioPrivilegio();
		usuarioPrivilegio2.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2.setFim(usuarioPrivilegio2Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2b = new UsuarioPrivilegio();
		usuarioPrivilegio2b.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2b.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2b.setFim(usuarioPrivilegio2Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());
		persistente.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio1);
		persistente.getPrivilegios().add(usuarioPrivilegio2);

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());
		nova.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		nova.getPrivilegios().add(usuarioPrivilegio2b);

		controller.update(persistente, nova);

		Assert.assertNotNull(persistente.getPrivilegios());
		Assert.assertEquals(persistente.getPrivilegios().size(), 1);

	}

	@Test
	public void updateComposicao5() {

		UsuarioPrivilegio usuarioPrivilegio1 = new UsuarioPrivilegio();
		usuarioPrivilegio1.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio1.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio1.setFim(usuarioPrivilegio1Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2 = new UsuarioPrivilegio();
		usuarioPrivilegio2.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2.setFim(usuarioPrivilegio2Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2b = new UsuarioPrivilegio();
		usuarioPrivilegio2b.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2b.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2b.setFim(usuarioPrivilegio2Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());
		persistente.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio2);

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());
		nova.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio1);
		nova.getPrivilegios().add(usuarioPrivilegio2b);

		controller.update(persistente, nova);

		Assert.assertNotNull(persistente.getPrivilegios());
		Assert.assertEquals(1, persistente.getPrivilegios().size());

	}

	@Test
	public void updateComposicao6() {

		UsuarioPrivilegio usuarioPrivilegio1 = new UsuarioPrivilegio();
		usuarioPrivilegio1.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio1.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio1.setFim(usuarioPrivilegio1Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2 = new UsuarioPrivilegio();
		usuarioPrivilegio2.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2.setFim(usuarioPrivilegio2Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio1b = new UsuarioPrivilegio();
		usuarioPrivilegio1b.setChave(usuarioPrivilegio1Ref.getChave());
		usuarioPrivilegio1b.setInicio(usuarioPrivilegio1Ref.getInicio());
		usuarioPrivilegio1b.setFim(usuarioPrivilegio1Ref.getFim());

		UsuarioPrivilegio usuarioPrivilegio2b = new UsuarioPrivilegio();
		usuarioPrivilegio2b.setChave(usuarioPrivilegio2Ref.getChave());
		usuarioPrivilegio2b.setInicio(usuarioPrivilegio2Ref.getInicio());
		usuarioPrivilegio2b.setFim(usuarioPrivilegio2Ref.getFim());

		Usuario persistente = new Usuario();
		persistente.setChave(persistenteRef.getChave());
		persistente.setNome(persistenteRef.getNome());
		persistente.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		persistente.getPrivilegios().add(usuarioPrivilegio1);
		persistente.getPrivilegios().add(usuarioPrivilegio2);

		Usuario nova = new Usuario();
		nova.setChave(persistenteRef.getChave());
		nova.setNome(persistenteRef.getNome());
		nova.setPrivilegios(new ArrayList<UsuarioPrivilegio>());
		nova.getPrivilegios().add(usuarioPrivilegio1b);
		nova.getPrivilegios().add(usuarioPrivilegio2b);

		controller.update(persistente, nova);

		Assert.assertNotNull(persistente.getPrivilegios());
		Assert.assertEquals(persistente.getPrivilegios().size(), 2);

	}

}

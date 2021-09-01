package br.com.esparda.usuario;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class AppTest {

	private UsuarioApp usuarioApp;

	@BeforeClass
	public static void beforeClass() {

	}

	@Before
	public void before() {
		usuarioApp = Mockito.spy(new UsuarioApp());

	}

	@Test
	public void testApp() {
		Assert.assertNotNull(usuarioApp);
	}
}

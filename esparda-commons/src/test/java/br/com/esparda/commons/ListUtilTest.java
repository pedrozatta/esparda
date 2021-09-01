package br.com.esparda.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class ListUtilTest {

	private UsuarioVO usuario1;
	private UsuarioVO usuario2;
	private UsuarioVO usuario3;

	private UsuarioVO usuario1b;
	private UsuarioVO usuario2b;
	private UsuarioVO usuario3b;

	@Before
	public void setUpBeforeClass() throws Exception {
		usuario1 = new UsuarioVO();
		usuario1.setChave(1L);
		usuario1.setNome("Nome 1");

		usuario2 = new UsuarioVO();
		usuario2.setChave(2L);
		usuario2.setNome("Nome 2");

		usuario3 = new UsuarioVO();
		usuario3.setChave(3L);
		usuario3.setNome("Nome 3");

		usuario1b = new UsuarioVO();
		usuario1b.setChave(1L);
		usuario1b.setNome("Nome 1b");

		usuario2b = new UsuarioVO();
		usuario2b.setChave(2L);
		usuario2b.setNome("Nome 2b");

		usuario3b = new UsuarioVO();
		usuario3b.setChave(2L);
		usuario3b.setNome("Nome 2b");

	}

	@Test
	public void test1() {
		List<UsuarioVO> antiga = new ArrayList<UsuarioVO>();
		Collections.addAll(antiga, usuario1);

		List<UsuarioVO> nova = new ArrayList<UsuarioVO>();
		Collections.addAll(nova, usuario1, usuario2);

		ListUtil<UsuarioVO> util = new ListUtil<UsuarioVO>(antiga, nova);

		Assert.assertEquals(1, util.getAdicionados().size());
		Assert.assertEquals(1, util.getIntersecao().size());
		Assert.assertEquals(0, util.getRemovidos().size());

	}

	@Test
	public void test2() {
		List<UsuarioVO> antiga = new ArrayList<UsuarioVO>();
		Collections.addAll(antiga, usuario1, usuario2);

		List<UsuarioVO> nova = new ArrayList<UsuarioVO>();
		Collections.addAll(nova, usuario2b, usuario3);

		ListUtil<UsuarioVO> util = new ListUtil<UsuarioVO>(antiga, nova);

		Assert.assertEquals(1, util.getAdicionados().size());
		Assert.assertEquals(1, util.getIntersecao().size());
		Assert.assertEquals(1, util.getRemovidos().size());

	}

	@Test
	public void test3() {
		List<UsuarioVO> antiga = new ArrayList<UsuarioVO>();
		Collections.addAll(antiga, usuario1, usuario2);

		List<UsuarioVO> nova = new ArrayList<UsuarioVO>();

		ListUtil<UsuarioVO> util = new ListUtil<>(antiga, nova);

		Assert.assertEquals(0, util.getAdicionados().size());
		Assert.assertEquals(0, util.getIntersecao().size());
		Assert.assertEquals(2, util.getRemovidos().size());

	}

	@Test
	public void test4() {
		List<UsuarioVO> antiga = new ArrayList<UsuarioVO>();

		List<UsuarioVO> nova = new ArrayList<UsuarioVO>();
		Collections.addAll(nova, usuario1, usuario2);

		ListUtil<UsuarioVO> util = new ListUtil<UsuarioVO>(antiga, nova);

		Assert.assertEquals(2, util.getAdicionados().size());
		Assert.assertEquals(0, util.getIntersecao().size());
		Assert.assertEquals(0, util.getRemovidos().size());

	}

	@Test
	public void test5() {
		List<UsuarioVO> antiga = new ArrayList<UsuarioVO>();
		Collections.addAll(antiga, usuario1, usuario2);

		List<UsuarioVO> nova = new ArrayList<UsuarioVO>();
		Collections.addAll(nova, usuario1, usuario2);

		ListUtil<UsuarioVO> util = new ListUtil<>(antiga, nova);

		Assert.assertEquals(0, util.getAdicionados().size());
		Assert.assertEquals(2, util.getIntersecao().size());
		Assert.assertEquals(0, util.getRemovidos().size());

	}

}

package br.com.esparda.usuario.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;
import br.com.esparda.usuario.domain.Usuario;
import br.com.esparda.usuario.repository.PrivilegioAcessoRepository;
import br.com.esparda.usuario.repository.UsuarioPrivilegioRepository;
import br.com.esparda.usuario.repository.UsuarioRepository;

@RestController
public class UsuarioController extends EntidadeController<Usuario, Long> {

	@Autowired
	public PrivilegioAcessoRepository privilegioAcessoRepository;
	@Autowired
	public UsuarioPrivilegioRepository usuarioPrivilegioRepository;

	@Autowired
	public UsuarioController(UsuarioRepository repository) {
		super(repository);
	}

	@RequestMapping("/autenticado")
	public Usuario user(Principal user) {
		if (user == null) {
			return null;
		}
		Usuario usuario = new Usuario();
		usuario.setNome(user.getName());
		return usuario;
	}

	@RequestMapping("/principal")
	public Principal principal(Principal user) {
		return user;
	}

//	@Override
//	protected Usuario save(Usuario entity) {
//		if (!Util.isNullOrEmpty(entity.getPrivilegios())) {
//			for (UsuarioPrivilegio usuarioPrivilegio : entity.getPrivilegios()) {
//				if (!Util.isNullOrEmpty(usuarioPrivilegio.getPrivilegioAcesso())) {
//					PrivilegioAcesso privilegio = privilegioAcessoRepository
//							.findOne(usuarioPrivilegio.getPrivilegioAcesso().getChave());
//					if (privilegio == null) {
//						privilegioAcessoRepository.save(usuarioPrivilegio.getPrivilegioAcesso());
//					} else {
//						usuarioPrivilegio.setPrivilegioAcesso(privilegio);
//					}
//				}
//				usuarioPrivilegioRepository.save(usuarioPrivilegio);
//			}
//		}
//		return super.save(entity);
//	}

	@Override
	public Iterable<Usuario> install() {
		String format = "Usuario %1d";
		long j = repository.count();
		List<Usuario> list = new ArrayList<Usuario>();
		for (int i = 1; i <= 5; i++) {
			Usuario item = new Usuario();
			item.setNome(String.format(format, i + j));
			list.add(item);
		}
		for (Usuario item : list) {
			this.save(item);
		}
		return list;
	}

}
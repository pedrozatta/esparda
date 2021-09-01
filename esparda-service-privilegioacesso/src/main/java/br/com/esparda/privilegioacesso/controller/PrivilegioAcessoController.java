package br.com.esparda.privilegioacesso.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;
import br.com.esparda.privilegioacesso.domain.PrivilegioAcesso;
import br.com.esparda.privilegioacesso.repository.PrivilegioAcessoRepository;

@RestController
public class PrivilegioAcessoController extends EntidadeController<PrivilegioAcesso, Long> {


	@Autowired
	public PrivilegioAcessoController(PrivilegioAcessoRepository repository) {
		super(repository);
	}

	@Override
	public Iterable<PrivilegioAcesso> install() {
		String format = "Privilegio Acesso %1d";
		long j = repository.count();
		List<PrivilegioAcesso> list = new ArrayList<PrivilegioAcesso>();
		for (int i = 1; i <= 5; i++) {
			PrivilegioAcesso item = new PrivilegioAcesso();
			item.setNome(String.format(format, i + j));
			list.add(item);
		}
		for (PrivilegioAcesso item : list) {
			this.save(item);
		}
		return list;
	}

}
package br.com.esparda.pessoa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;
import br.com.esparda.pessoa.domain.Pessoa;
import br.com.esparda.pessoa.repository.PessoaRepository;

@RestController
public class PessoaController extends EntidadeController<Pessoa, Long> {
	
	@Autowired
	public PessoaController(PessoaRepository repository) {
		super(repository);
	}

	@Override
	public Iterable<Pessoa> install() {
		String format = "Pessoa %1d";
		long j = repository.count();
		List<Pessoa> list = new ArrayList<Pessoa>();
		for (int i = 1; i <= 5; i++) {
			Pessoa item = new Pessoa();
			item.setNome(String.format(format, i + j));
			list.add(item);
		}
		for (Pessoa item : list) {
			this.save(item);
		}
		return list;
	}

}
package br.com.esparda.processo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;
import br.com.esparda.processo.domain.Processo;
import br.com.esparda.processo.repository.ProcessoRepository;

@RestController
public class ProcessoController extends EntidadeController<Processo, Long> {

	@Autowired
	public ProcessoController(ProcessoRepository repository) {
		super(repository);
	}

	@Override
	public Iterable<Processo> install() {
		String format = "Processo %1d";
		long j = repository.count();
		List<Processo> list = new ArrayList<Processo>();
		for (int i = 1; i <= 5; i++) {
			Processo item = new Processo();
			item.setNome(String.format(format, i + j));
			list.add(item);
		}
		for (Processo item : list) {
			this.save(item);
		}
		return list;
	}

}
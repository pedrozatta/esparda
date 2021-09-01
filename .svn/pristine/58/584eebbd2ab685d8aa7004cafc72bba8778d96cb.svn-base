package br.com.esparda.orcamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.orcamento.domain.Orcamento;
import br.com.esparda.orcamento.repository.OrcamentoRepository;

@RestController
public class OrcamentoController extends EntidadeController<Orcamento, Long> {
	
	@Autowired
	public OrcamentoController(OrcamentoRepository repository) {
		super(repository);
	}

}
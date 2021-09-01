package br.com.esparda.atividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.atividade.domain.Atividade;
import br.com.esparda.atividade.repository.AtividadeRepository;

@RestController
public class AtividadeController extends EntidadeController<Atividade, Long> {
	
	@Autowired
	public AtividadeController(AtividadeRepository repository) {
		super(repository);
	}

}
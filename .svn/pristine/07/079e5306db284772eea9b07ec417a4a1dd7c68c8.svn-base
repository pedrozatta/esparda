package br.com.esparda.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.sistema.domain.Sistema;
import br.com.esparda.sistema.repository.SistemaRepository;

@RestController
public class SistemaController extends EntidadeController<Sistema, Long> {
	
	@Autowired
	public SistemaController(SistemaRepository repository) {
		super(repository);
	}

}
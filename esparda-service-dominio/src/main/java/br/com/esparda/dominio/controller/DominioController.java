package br.com.esparda.dominio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.dominio.domain.Dominio;
import br.com.esparda.dominio.repository.DominioRepository;

@RestController
public class DominioController extends EntidadeController<Dominio, Long> {
	
	@Autowired
	public DominioController(DominioRepository repository) {
		super(repository);
	}

}
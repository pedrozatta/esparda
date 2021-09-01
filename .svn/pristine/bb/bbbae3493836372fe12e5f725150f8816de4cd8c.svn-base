package br.com.esparda.categoria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.categoria.domain.Categoria;
import br.com.esparda.categoria.repository.CategoriaRepository;

@RestController
public class CategoriaController extends EntidadeController<Categoria, Long> {
	
	@Autowired
	public CategoriaController(CategoriaRepository repository) {
		super(repository);
	}

}
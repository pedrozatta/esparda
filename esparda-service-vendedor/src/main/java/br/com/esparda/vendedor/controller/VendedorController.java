package br.com.esparda.vendedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.vendedor.domain.Vendedor;
import br.com.esparda.vendedor.repository.VendedorRepository;

@RestController
public class VendedorController extends EntidadeController<Vendedor, Long> {
	
	@Autowired
	public VendedorController(VendedorRepository repository) {
		super(repository);
	}

}
package br.com.esparda.imagem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.imagem.domain.Imagem;
import br.com.esparda.imagem.repository.ImagemRepository;

@RestController
public class ImagemController extends EntidadeController<Imagem, Long> {
	
	@Autowired
	public ImagemController(ImagemRepository repository) {
		super(repository);
	}

}
package br.com.esparda.mensagem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.mensagem.domain.Mensagem;
import br.com.esparda.mensagem.repository.MensagemRepository;

@RestController
public class MensagemController extends EntidadeController<Mensagem, Long> {
	
	@Autowired
	public MensagemController(MensagemRepository repository) {
		super(repository);
	}

}
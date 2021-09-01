package br.com.esparda.solicitacaoorcamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.solicitacaoorcamento.domain.SolicitacaoOrcamento;
import br.com.esparda.solicitacaoorcamento.repository.SolicitacaoOrcamentoRepository;

@RestController
public class SolicitacaoOrcamentoController extends EntidadeController<SolicitacaoOrcamento, Long> {

	@Autowired
	public SolicitacaoOrcamentoController(SolicitacaoOrcamentoRepository repository) {
		super(repository);
	}

	@Override
	public Iterable<SolicitacaoOrcamento> install() {
		carregaDominioStatus();
		return super.install();
	}

	private void carregaDominioStatus() {
		// TODO PEDROZATTA FUTURAMENTO BUSCAR ESSES DADOS NO MICROSERVICE DE
		// DOMINIO 

		// TODO VITOR Remover todos dominios de Status
		// TODO VITOR Gerar dominios de Status e persistir na base
	}

}
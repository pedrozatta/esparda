package br.com.esparda.orcamento.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.orcamento.domain.Orcamento;

@Repository
public interface  OrcamentoRepository extends EspardaRepository<Orcamento,Long>{

}
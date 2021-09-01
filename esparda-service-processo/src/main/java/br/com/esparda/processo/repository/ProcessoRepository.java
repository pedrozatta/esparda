package br.com.esparda.processo.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.processo.domain.Processo;

@Repository
public interface ProcessoRepository extends EspardaRepository<Processo, Long> {

}
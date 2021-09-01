package br.com.esparda.sistema.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.sistema.domain.Sistema;

@Repository
public interface  SistemaRepository extends EspardaRepository<Sistema,Long>{

}
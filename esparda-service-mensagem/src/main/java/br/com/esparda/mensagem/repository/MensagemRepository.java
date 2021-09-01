package br.com.esparda.mensagem.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.mensagem.domain.Mensagem;

@Repository
public interface  MensagemRepository extends EspardaRepository<Mensagem,Long>{

}
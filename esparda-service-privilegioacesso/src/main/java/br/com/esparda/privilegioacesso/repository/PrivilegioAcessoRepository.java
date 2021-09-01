package br.com.esparda.privilegioacesso.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.privilegioacesso.domain.PrivilegioAcesso;

@Repository
public interface PrivilegioAcessoRepository extends EspardaRepository<PrivilegioAcesso, Long> {

}
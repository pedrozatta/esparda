package br.com.esparda.${serviceLowerCase}.repository;

import org.springframework.stereotype.Repository;

import br.com.esparda.commons.EspardaRepository;
import br.com.esparda.${serviceLowerCase}.domain.${service};

@Repository
public interface  ${service}Repository extends EspardaRepository<${service},Long>{

}
package br.com.esparda.${serviceLowerCase}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.esparda.commons.EntidadeController;

import br.com.esparda.${serviceLowerCase}.domain.${service};
import br.com.esparda.${serviceLowerCase}.repository.${service}Repository;

@RestController
public class ${service}Controller extends EntidadeController<${service}, Long> {
	
	@Autowired
	public ${service}Controller(${service}Repository repository) {
		super(repository);
	}

}
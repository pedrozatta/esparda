package br.com.esparda.commons;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.esparda.commons.evento.EventoService;

public abstract class EntidadeController<E extends Entidade<K>, K extends Number> extends AbstractController {

	// @Autowired
	// private ApplicationContext applicationContext;

	@Autowired
	public EventoService eventoService;

	protected final EspardaRepository<E, K> repository;

	public EntidadeController(EspardaRepository<E, K> repository) {
		this.repository = repository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<E> findAll() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public E findById(@PathVariable K id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/clean", method = RequestMethod.GET)
	public void clean() {
		for (E entity : this.findAll()) {
			this.remove(entity);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public E removeById(@PathVariable K id) {
		E entity = repository.findOne(id);
		return this.remove(entity);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public E create(@RequestBody E entity) {
		return this.save(entity);
	}

	@RequestMapping(value = "/install", method = RequestMethod.GET)
	public Iterable<E> install() {
		return null;
	}

	protected E remove(E entity) {
		repository.delete(entity);
		eventoService.sendToStore(entity, true);
		return entity;
	}

	protected E save(E entity) {
		processaAssociacoes(entity, null);
		repository.saveAndFlush(entity);
		eventoService.sendToStore(entity, false);
		return entity;
	}

}

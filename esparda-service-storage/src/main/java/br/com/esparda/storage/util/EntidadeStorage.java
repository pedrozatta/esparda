package br.com.esparda.storage.util;

import javax.persistence.Embedded;

import br.com.esparda.commons.Entidade;

/**
 * 
 * @author p-pzatta
 *
 * @param <T>
 */
public abstract class EntidadeStorage<T> extends Entidade<T> {

	@Embedded
	private Evento ultimoEvento;

	public Evento getUltimoEvento() {
		return ultimoEvento;
	}

	public void setUltimoEvento(Evento ultimoEvento) {
		this.ultimoEvento = ultimoEvento;
	}

}

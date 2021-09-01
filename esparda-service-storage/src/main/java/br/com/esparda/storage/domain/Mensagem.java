package br.com.esparda.storage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.esparda.commons.Entidade;

@Entity
public class Mensagem extends Entidade<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chave;

	public Long getChave() {
		return chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

}


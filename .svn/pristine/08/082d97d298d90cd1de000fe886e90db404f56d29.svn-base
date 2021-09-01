package br.com.esparda.processo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.esparda.commons.Entidade;

@Entity
public class Processo extends Entidade<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chave;

	private String nome;

	public Long getChave() {
		return chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

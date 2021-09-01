package br.com.esparda.storage.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.domain.IProcesso;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@EntidadeAnnotation
public class Processo extends Entidade<Long> implements IProcesso {

	@Id
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

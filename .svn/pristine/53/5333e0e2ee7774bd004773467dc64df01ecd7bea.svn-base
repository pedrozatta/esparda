package br.com.esparda.storage.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.domain.IPrivilegioAcesso;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@EntidadeAnnotation
public class PrivilegioAcesso extends Entidade<Long> implements IPrivilegioAcesso {

	@Id
	private Long chave;

	private String nome;

	@Override
	public Long getChave() {
		return chave;
	}

	@Override
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

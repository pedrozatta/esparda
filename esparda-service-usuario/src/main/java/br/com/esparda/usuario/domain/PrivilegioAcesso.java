package br.com.esparda.usuario.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.domain.IPrivilegioAcessoBasic;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@Table(name = "PRIVILEGIO")
@EntidadeAnnotation
public class PrivilegioAcesso extends Entidade<Long> implements IPrivilegioAcessoBasic {

	@Id
	@Column(name = "CHAVE")
	private Long chave;

	@Column(name = "NOME")
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

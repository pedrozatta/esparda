package br.com.esparda.mock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@Table(name = "USUARIO")
@EntidadeAnnotation
public class UsuarioPrivilegioImpl extends Entidade<Long> {

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

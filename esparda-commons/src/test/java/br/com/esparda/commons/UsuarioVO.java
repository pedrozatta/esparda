package br.com.esparda.commons;

import br.com.esparda.commons.meta.AtributoAnnotation;

public class UsuarioVO extends Entidade<Long> {

	private Long chave;

	@AtributoAnnotation(tamanho = 50)
	private String nome;

	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

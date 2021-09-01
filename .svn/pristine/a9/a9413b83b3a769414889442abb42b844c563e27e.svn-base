package br.com.esparda.mock;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.meta.AgregacaoAnnotation;
import br.com.esparda.commons.meta.AtributoAnnotation;
import br.com.esparda.commons.meta.Cardinalidade;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@Table(name = "USUARIO")
@EntidadeAnnotation
public class UsuarioAgregacaoImpl extends Entidade<Long> {

	@Id
	@Column(name = "CHAVE")
	private Long chave;

	@Column(name = "NOME")
	private String nome;

	@AtributoAnnotation(nome = "Privilégios de Acesso")
	@AgregacaoAnnotation(cardinalidade = Cardinalidade.MUITOS)
	private List<UsuarioPrivilegioImpl> privilegios;

	@AtributoAnnotation(nome = "Privilégio de Acesso")
	@AgregacaoAnnotation(cardinalidade = Cardinalidade.UM)
	private UsuarioPrivilegioImpl privilegio;

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

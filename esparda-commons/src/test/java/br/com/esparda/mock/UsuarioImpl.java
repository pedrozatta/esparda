package br.com.esparda.mock;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.domain.IUsuario;
import br.com.esparda.commons.meta.AtributoAnnotation;
import br.com.esparda.commons.meta.Cardinalidade;
import br.com.esparda.commons.meta.ComposicaoAnnotation;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@Table(name = "USUARIO")
@EntidadeAnnotation
public class UsuarioImpl extends Entidade<Long> implements IUsuario {

	@Id
	@Column(name = "CHAVE")
	private Long chave;

	@Column(name = "NOME")
	private String nome;

	@AtributoAnnotation(nome = "Privilégios de Acesso")
	@ComposicaoAnnotation(cardinalidade = Cardinalidade.MUITOS)
	private List<UsuarioPrivilegioImpl> privilegios;

	@AtributoAnnotation(nome = "Privilégio de Acesso")
	@ComposicaoAnnotation(cardinalidade = Cardinalidade.UM)
	private UsuarioPrivilegioImpl privilegio;

	public List<UsuarioPrivilegioImpl> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(List<UsuarioPrivilegioImpl> privilegios) {
		this.privilegios = privilegios;
	}

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

	public UsuarioPrivilegioImpl getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(UsuarioPrivilegioImpl privilegio) {
		this.privilegio = privilegio;
	}

}

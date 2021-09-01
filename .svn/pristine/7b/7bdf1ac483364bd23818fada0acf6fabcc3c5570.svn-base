package br.com.esparda.usuario.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.esparda.commons.Entidade;
import br.com.esparda.commons.domain.IUsuarioPrivilegio;
import br.com.esparda.commons.meta.AgregacaoAnnotation;
import br.com.esparda.commons.meta.EntidadeAnnotation;

@Entity
@Table(name = "USUARIO_PRIVILEGIO")
@EntidadeAnnotation
public class UsuarioPrivilegio extends Entidade<Long> implements IUsuarioPrivilegio  {

	@Id
	@Column(name = "CHAVE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chave;

	@Column(name = "DT_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;

	@Column(name = "DT_FIM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fim;

	@ManyToOne
	@JoinColumn(name = "PRIVILEGIO_ID")
	@AgregacaoAnnotation
	private PrivilegioAcesso privilegioAcesso;

	public Long getChave() {
		return chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public PrivilegioAcesso getPrivilegioAcesso() {
		return privilegioAcesso;
	}

	public void setPrivilegioAcesso(PrivilegioAcesso privilegioAcesso) {

		this.privilegioAcesso = privilegioAcesso;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

}

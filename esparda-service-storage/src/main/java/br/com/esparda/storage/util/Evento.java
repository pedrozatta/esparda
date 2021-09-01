package br.com.esparda.storage.util;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Evento {
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String usuario;
	private EventoTipo tipo;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public EventoTipo getTipo() {
		return tipo;
	}

	public void setTipo(EventoTipo tipo) {
		this.tipo = tipo;
	}

}

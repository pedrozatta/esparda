package br.com.esparda.monitor.domain;

import java.util.Date;

public class MonitorService {

	public static final String STATUS_ATIVO = "ATIVO";
	public static final String STATUS_INATIVO = "INATIVO";
	public static final String STATUS_PENDENTE = "PENDENTE";

	private long quantidade;
	private String info;
	private Date primeira;
	private Date ultima;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MonitorService() {
	}

	public MonitorService(String info, Date date) {
		super();
		this.info = info;
		this.primeira = date;
		this.status = STATUS_ATIVO;
	}

	public Date getPrimeira() {
		return primeira;
	}

	public void setPrimeira(Date primeira) {
		this.primeira = primeira;
	}

	public Date getUltima() {
		return ultima;
	}

	public void setUltima(Date ultima) {
		this.ultima = ultima;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}

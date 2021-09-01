package br.com.esparda.commons.vo;

import java.util.List;

public class ServicoVO {

	private String esparda;
	private String nome;
	private String implementacao;
	private String servidor;
	private List<ServicoItemVO> itens;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImplementacao() {
		return implementacao;
	}

	public void setImplementacao(String implementacao) {
		this.implementacao = implementacao;
	}

	public List<ServicoItemVO> getItens() {
		return itens;
	}

	public void setItens(List<ServicoItemVO> itens) {
		this.itens = itens;
	}


	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getEsparda() {
		return esparda;
	}

	public void setEsparda(String esparda) {
		this.esparda = esparda;
	}
}

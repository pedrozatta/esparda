package br.com.esparda.commons.vo;

public class IntervaloVO<T> {

	private T minimo;
	private T maximo;

	public T getMinimo() {
		return minimo;
	}

	public void setMinimo(T minimo) {
		this.minimo = minimo;
	}

	public T getMaximo() {
		return maximo;
	}

	public void setMaximo(T maximo) {
		this.maximo = maximo;
	}

}

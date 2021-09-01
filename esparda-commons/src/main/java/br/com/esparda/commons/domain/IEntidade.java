package br.com.esparda.commons.domain;

public interface IEntidade<T> {

	public void setChave(T chave);

	public T getChave();

}

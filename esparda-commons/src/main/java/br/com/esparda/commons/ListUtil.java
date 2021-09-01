package br.com.esparda.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil<T> {

	private List<T> antiga;
	private List<T> nova;

	private List<T> removidos;
	private List<T> adicionados;
	private Map<T, T> intersecao;

	public ListUtil() {
		removidos = new ArrayList<T>();
		adicionados = new ArrayList<T>();
		intersecao = new HashMap<T, T>();
	}

	public ListUtil(List<T> antiga, List<T> nova) {
		removidos = new ArrayList<T>();
		adicionados = new ArrayList<T>();
		intersecao = new HashMap<T, T>();

		this.antiga = antiga;
		this.nova = nova;

		refresh();
	}

	public void refresh() {

		adicionados.clear();
		removidos.clear();
		intersecao.clear();

		if (this.antiga == null && this.nova == null) {
			return;
		}

		if (this.antiga == null && this.nova != null) {
			adicionados.addAll(this.nova);
			return;
		}

		if (this.antiga != null && this.nova == null) {
			removidos.addAll(this.antiga);
			return;
		}

		// TODO: Melhorar performance dessa algoritimo
		for (T novaItem : this.nova) {
			if (!this.antiga.contains(novaItem)) {
				adicionados.add(novaItem);
			}
		}
		for (T antigaItem : this.antiga) {
			if (!this.nova.contains(antigaItem)) {
				removidos.add(antigaItem);
			}
		}

		for (T novaItem : this.nova) {
			for (T antigaItem : this.antiga) {
				if (novaItem.equals(antigaItem)) {
					intersecao.put(antigaItem, novaItem);
				}
			}
		}

	}

	public List<T> getAntiga() {
		return antiga;
	}

	public void setAntiga(List<T> antiga) {
		this.antiga = antiga;
	}

	public List<T> getNova() {
		return nova;
	}

	public void setNova(List<T> nova) {
		this.nova = nova;
	}

	public List<T> getRemovidos() {
		return removidos;
	}

	public List<T> getAdicionados() {
		return adicionados;
	}

	public Map<T, T> getIntersecao() {
		return intersecao;
	}
}

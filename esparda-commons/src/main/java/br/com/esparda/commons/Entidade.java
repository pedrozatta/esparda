package br.com.esparda.commons;

import br.com.esparda.commons.domain.IEntidade;

public abstract class Entidade<T> implements IEntidade<T> {

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entidade)) {
			return false;
		}
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}
		Entidade<?> entidade = (Entidade<?>) obj;
		if (this.getChave() == null || entidade.getChave() == null) {
			return false;
		}
		if (!entidade.getChave().equals(this.getChave())) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		if (this.getChave() == null) {
			return 0;
		}
		return this.getChave().hashCode();
	}

}

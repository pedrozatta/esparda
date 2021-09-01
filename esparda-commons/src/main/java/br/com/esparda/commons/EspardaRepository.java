package br.com.esparda.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EspardaRepository<E extends Entidade<K>, K extends Number>
		extends JpaRepository<E, K>, JpaSpecificationExecutor<E> {

}

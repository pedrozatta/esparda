package br.com.esparda.commons.evento;

public enum EventoTipo {

	PERSISTENCIA_EXCLUIR("PersistenciaExcluir"), PERSISTENCIA("Persistencia"), MICROSERVICE_STATUS(
			"MicroserviceStatus");

	public final String chave;

	private EventoTipo(String chave) {
		this.chave = chave;
	}

}

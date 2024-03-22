package br.com.magnasistemas.construcaocivil.exception;

public class CustomDataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomDataIntegrityException(String mensagem) {
		super(mensagem);
	}
}

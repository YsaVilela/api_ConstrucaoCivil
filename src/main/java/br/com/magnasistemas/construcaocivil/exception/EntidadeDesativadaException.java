package br.com.magnasistemas.construcaocivil.exception;

public class EntidadeDesativadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeDesativadaException(String mensage) {
        super(mensage);
    }

}

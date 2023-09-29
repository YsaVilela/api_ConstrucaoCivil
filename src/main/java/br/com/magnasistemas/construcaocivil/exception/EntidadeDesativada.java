package br.com.magnasistemas.construcaocivil.exception;

public class EntidadeDesativada extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeDesativada(String mensage) {
        super(mensage);
    }

}

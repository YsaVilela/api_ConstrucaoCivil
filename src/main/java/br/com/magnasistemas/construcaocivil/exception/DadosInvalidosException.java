package br.com.magnasistemas.construcaocivil.exception;

public class DadosInvalidosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public DadosInvalidosException(String mensage) {
        super(mensage);
    }

}

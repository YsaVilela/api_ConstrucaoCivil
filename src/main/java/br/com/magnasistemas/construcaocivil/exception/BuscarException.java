package br.com.magnasistemas.construcaocivil.exception;

public class BuscarException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BuscarException(String mensage) {
        super(mensage);
    }

}

package br.com.magnasistemas.construcaocivil.service.validacoes.construtora;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;

@Component
public class ExisteConstrutora implements ValidadorConstrutora{
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Override
	public void validar(Long id) {
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(id);
		if (validarConstrutora.isEmpty()) 
			throw new InvalidContentException ("Construtora não encontrada");
	} 

}

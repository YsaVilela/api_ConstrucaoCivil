package br.com.magnasistemas.construcaocivil.service.validacoes.profissional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@Component
public class ExisteProfissional implements ValidadorProfissional {

	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Override
	public void validar(Long id) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new InvalidContentException ("Profissional não encontrado");		
	}

}

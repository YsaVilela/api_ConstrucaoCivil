package br.com.magnasistemas.construcaocivil.service.validacoes.profissional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativadaException;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@Component
public class StatusProfissional implements ValidadorProfissional {

	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Override
	public void validar(Long id) {
		Profissional profissional = profissionalRepository.getReferenceById(id); 
		if (!profissional.isStatus())
			throw new EntidadeDesativadaException("Profissional desativado");		
	}

}

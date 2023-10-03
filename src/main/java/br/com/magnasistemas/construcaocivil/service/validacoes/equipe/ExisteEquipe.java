package br.com.magnasistemas.construcaocivil.service.validacoes.equipe;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;

@Component
public class ExisteEquipe implements ValidadorEquipe{
	
	@Autowired
	private EquipeRepository equipeRepository;

	@Override
	public void validar(Long id) {
		Optional<Equipe> validarProfissional = equipeRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Equipe não encontrado");		
	}

}

package br.com.magnasistemas.construcaocivil.service.validacoes.equipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativada;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;

@Component
public class StatusEquipe implements ValidadorEquipe{

	@Autowired
	private EquipeRepository equipeRepository;

	@Override
	public void validar(Long id) {
		Equipe equipe = equipeRepository.getReferenceById(id);
		if (!equipe.isStatus())
			throw new EntidadeDesativada("Equipe desativada");
	}
	

}

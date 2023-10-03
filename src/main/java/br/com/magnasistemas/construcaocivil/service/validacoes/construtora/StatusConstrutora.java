package br.com.magnasistemas.construcaocivil.service.validacoes.construtora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativada;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;

@Component
public class StatusConstrutora implements ValidadorConstrutora{

	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Override
	public void validar(Long id) {
		Construtora construtora = construtoraRepository.getReferenceById(id);
		if (!construtora.isStatus())
			throw new EntidadeDesativada ("Construtora desativada");
	}
	

}

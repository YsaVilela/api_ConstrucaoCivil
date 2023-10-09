package br.com.magnasistemas.construcaocivil.service.validacoes.cargo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;

@Component
public class ValidadorBuscaCargo implements ValidadorCargo{
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Override
	public void validar(Long id) {
		Optional<Cargo> validarCargo = cargoRepository.findById(id);
		if (validarCargo.isEmpty()) 
			throw new InvalidContentException ("Cargo não encontrado");		
	}
	
	

}

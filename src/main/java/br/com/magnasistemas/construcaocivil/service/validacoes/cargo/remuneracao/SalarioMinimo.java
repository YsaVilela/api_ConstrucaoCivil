package br.com.magnasistemas.construcaocivil.service.validacoes.cargo.remuneracao;

import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.exception.InvalidDataException;

@Component
public class SalarioMinimo implements ValidadorRemuneracaoCargo {

	@Override
	public void validar(Cargo cargo, Double remuneracao) {
		if (remuneracao < 1320.00)
			throw new InvalidDataException("Salário mínimo é de 1320.00");
	}

}

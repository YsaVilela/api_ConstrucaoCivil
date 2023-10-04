package br.com.magnasistemas.construcaocivil.service.validacoes.cargo.remuneracao;

import org.springframework.stereotype.Component;

import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.exception.DadosInvalidosException;

@Component
public class ReduzirRemuneracao implements ValidadorRemuneracaoCargo  {

	@Override
	public void validar(Cargo cargo, Double remuneracao) {
		if (cargo.getRemuneracao() == null)
			cargo.setRemuneracao(0.0);
		if (cargo.getRemuneracao() > remuneracao)
			throw new DadosInvalidosException("O salário não pode ser reduzido");		
	}

}

package br.com.magnasistemas.construcaocivil.service.validacoes.cargo.remuneracao;

import br.com.magnasistemas.construcaocivil.entity.Cargo;

public interface ValidadorRemuneracaoCargo {

	void validar (Cargo cargo, Double remuneracao);
}

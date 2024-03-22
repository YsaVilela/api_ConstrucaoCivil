package br.com.magnasistemas.construcaocivil.dto.dominio;

import br.com.magnasistemas.construcaocivil.entity.dominio.Estados;

public record DadosEstados(
		Long id,
		String nome,
		String uf,
		String região) {
	
	public DadosEstados (Estados estado) {
		this(estado.getId(),
				estado.getNome(),
				estado.getUf(),
				estado.getRegiao());
	}
}

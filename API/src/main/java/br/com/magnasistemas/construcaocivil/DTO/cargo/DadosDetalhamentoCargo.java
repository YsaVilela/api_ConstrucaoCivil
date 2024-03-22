package br.com.magnasistemas.construcaocivil.dto.cargo;

import br.com.magnasistemas.construcaocivil.entity.Cargo;

public record DadosDetalhamentoCargo(
		Long id,
		String nome,
		Double remuneracao) {

	public DadosDetalhamentoCargo (Cargo cargo) {
		this(cargo.getId(),
				cargo.getNome(),
				cargo.getRemuneracao());
	}
}

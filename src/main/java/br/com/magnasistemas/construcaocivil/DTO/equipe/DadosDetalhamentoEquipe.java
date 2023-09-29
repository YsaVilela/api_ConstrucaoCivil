package br.com.magnasistemas.construcaocivil.DTO.equipe;

import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.enumerator.Turno;

public record DadosDetalhamentoEquipe(
		Long id,
		Construtora construtora,
		String nome,
		Turno turno,
		boolean status) {
	
	public DadosDetalhamentoEquipe (Equipe equipe) {
		this(equipe.getId(),
				equipe.getConstrutora(),
				equipe.getNome(),
				equipe.getTurno(),
				equipe.isStatus());
	}

}

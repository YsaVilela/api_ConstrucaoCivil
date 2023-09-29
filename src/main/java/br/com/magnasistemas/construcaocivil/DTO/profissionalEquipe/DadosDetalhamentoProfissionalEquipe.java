package br.com.magnasistemas.construcaocivil.DTO.profissionalEquipe;

import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.entity.ProfissionalEquipe;

public record DadosDetalhamentoProfissionalEquipe(
		Long id,
		Equipe idEquipe,
		Profissional idProfissional) {
	
	public DadosDetalhamentoProfissionalEquipe (ProfissionalEquipe profissionalEquipe) {
		this(profissionalEquipe.getId(),
				profissionalEquipe.getEquipe(),
				profissionalEquipe.getProfissional());
	}

}

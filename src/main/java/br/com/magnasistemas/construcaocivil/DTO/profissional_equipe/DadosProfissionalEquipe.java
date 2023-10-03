package br.com.magnasistemas.construcaocivil.dto.profissional_equipe;

import jakarta.validation.constraints.NotNull;

public record DadosProfissionalEquipe(
		@NotNull (message = "O ID da equipe é obrigatório")
		Long idEquipe,
		
		@NotNull (message = "O ID do profissional é obrigatório")
		Long idProfissional) {

}

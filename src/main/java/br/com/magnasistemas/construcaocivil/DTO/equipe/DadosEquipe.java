package br.com.magnasistemas.construcaocivil.DTO.equipe;

import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import jakarta.validation.constraints.NotNull;

public record DadosEquipe(
		@NotNull (message = "O ID da construtora é obrigatório")
		Long idConstrutora,
		
		@NotNull (message = "O nome é obrigatório")
		String nome,
		
		@NotNull (message = "O turno é obrigatório")
		Turno turno) {

}

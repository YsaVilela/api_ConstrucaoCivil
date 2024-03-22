package br.com.magnasistemas.construcaocivil.dto.equipe;

import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarEquipe(
		@NotNull (message = "O ID é obrigatório")
		Long id,
		
		@NotNull (message = "O ID da construtora é obrigatório")
		Long idConstrutora,
		
		@NotNull (message = "O nome é obrigatório")
		String nome,
		
		@NotNull (message = "O turno é obrigatório")
		Turno turno) {

}

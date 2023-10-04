package br.com.magnasistemas.construcaocivil.dto.equipe;

import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosEquipe(
		@NotNull (message = "O ID da construtora é obrigatório")
		Long idConstrutora,
		
		@NotBlank (message = "O nome é obrigatório")
		String nome,
		
		@NotNull (message = "O turno é obrigatório")
		Turno turno) {

}

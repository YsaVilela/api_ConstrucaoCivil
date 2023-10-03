package br.com.magnasistemas.construcaocivil.dto.cargo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCargo(
		@NotBlank (message = "Nome é obrigatório")
		String nome,
		
		@NotNull (message = "Remuneraçao é obrigatório")
		Double remuneracao) {

}

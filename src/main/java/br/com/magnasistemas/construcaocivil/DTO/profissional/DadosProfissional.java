package br.com.magnasistemas.construcaocivil.DTO.profissional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProfissional(
		@NotNull (message = "O ID da construtora é obrigatório")
		Long idConstrutora,
		
		@NotBlank (message = "CPF é obrigatório")
		String cpf,
		
		@NotBlank (message = "Nome é obrigatório")
		String nome,
		
		@NotBlank (message = "Telefone é obrigatório")
		String telefone,
		
		@NotNull (message = "O ID do cargo é obrigatório")
		Long idCargo) {

}

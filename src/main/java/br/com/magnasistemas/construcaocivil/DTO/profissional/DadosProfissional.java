package br.com.magnasistemas.construcaocivil.dto.profissional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosProfissional(
		@NotNull (message = "O ID da construtora é obrigatório")
		Long idConstrutora,
		
		@NotBlank (message = "CPF é obrigatório")
		@Pattern(regexp = "\\d{11}", message = "Telefone inválido")
		String cpf,
		
		@NotBlank (message = "Nome é obrigatório")
		String nome,
		
		@NotBlank (message = "Telefone é obrigatório")
		@Pattern(regexp = "\\d{11}", message = "Telefone inválido")
		String telefone,
		
		@NotNull (message = "O ID do cargo é obrigatório")
		Long idCargo) {

}

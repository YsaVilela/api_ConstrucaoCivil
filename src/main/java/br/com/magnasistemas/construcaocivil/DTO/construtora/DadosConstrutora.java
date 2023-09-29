package br.com.magnasistemas.construcaocivil.DTO.construtora;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosConstrutora(
		@NotBlank (message = "CNPJ é obrigatório")
		@Pattern(regexp = "\\d{11}", message = "CNPJ inválido")
		String cnpj,
		
		@NotBlank (message = "Nome é obrigatório")
		String nome,
		
		@NotNull (message = "Telefone é obrigatório")
		@Pattern(regexp = "\\d{11}", message = "Telefone inválido")
		String telefone,
		
		@Email (message = "Email inválido")
		String email) {
}

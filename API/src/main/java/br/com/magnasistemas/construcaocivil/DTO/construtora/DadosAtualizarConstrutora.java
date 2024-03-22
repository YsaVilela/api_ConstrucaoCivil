package br.com.magnasistemas.construcaocivil.dto.construtora;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizarConstrutora(
		@NotNull (message = "Campo Id é obrigatótio")
		Long id,
		
		@NotBlank (message = "CPF é obrigatório")
		@Pattern(regexp = "\\d{14}", message = "Telefone inválido")
		String cnpj,
		
		@NotBlank (message = "Nome não pode estar vazio")
		String nome,
		
		@Pattern(regexp = "\\d{10,11}", message = "Telefone inválido")
		String telefone,
		
		@Email (message = "Email inválido")
		String email) {

}

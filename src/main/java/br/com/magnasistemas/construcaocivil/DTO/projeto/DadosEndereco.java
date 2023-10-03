package br.com.magnasistemas.construcaocivil.dto.projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosEndereco (
		@NotBlank (message = "CEP não pode ser vazio")
		String cep,
		
		@NotBlank (message = "Logradouro não pode ser vazio")
		String logradouro,
		
		@NotNull(message = "Número não pode ser vazio")
		Long numero,
		
		String complemento,
		
		@NotNull(message = "Cidade não pode ser vazio")
		Long idCidade){
}

package br.com.magnasistemas.construcaocivil.DTO.projeto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProjeto(
		@NotNull (message = "ID não pode ser vazio")
		Long idConstrutora,
		
		@NotBlank (message = "Nome não pode ser vazio")
		String nome,
		
		@NotNull(message = "Orçamento Máximo não pode ser vazio")
		Double orcamentoMaximo,

		String descricao,
		
		@Valid
		DadosEndereco endereco) 
{

}

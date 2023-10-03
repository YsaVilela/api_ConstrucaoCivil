package br.com.magnasistemas.construcaocivil.dto.projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DadosAtualizarProjeto(	
		@NotNull (message = "ID não pode ser vazio")
		Long id,
		
		@NotBlank (message = "Nome não pode ser vazio")
		String nome,
		
		@NotNull (message = "Orçamento máximo não pode ser vazio")
		Double orcamentoMaximo,
		
		String descricao)  {

	
}

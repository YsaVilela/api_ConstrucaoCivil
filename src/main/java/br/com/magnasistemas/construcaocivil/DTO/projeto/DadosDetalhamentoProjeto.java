package br.com.magnasistemas.construcaocivil.DTO.projeto;

import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Projeto;

public record DadosDetalhamentoProjeto(
		Long id,
		Construtora construtora,
		String nome,
		Double orcamentoMaximo,
		String descricao) {
	
	public DadosDetalhamentoProjeto (Projeto projeto) {
		this(projeto.getId(),
				projeto.getConstrutora(),
				projeto.getNome(),
				projeto.getOrcamentoMaximo(),
				projeto.getDescricao());
	}

}

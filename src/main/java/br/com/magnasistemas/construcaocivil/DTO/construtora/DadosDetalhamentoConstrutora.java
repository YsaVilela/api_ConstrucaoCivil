package br.com.magnasistemas.construcaocivil.DTO.construtora;

import br.com.magnasistemas.construcaocivil.entity.Construtora;

public record DadosDetalhamentoConstrutora(
		Long id,
		String nome,
		String cnpj,
		String telefone,
		String email,
		boolean status) {
	
	public DadosDetalhamentoConstrutora (Construtora construtora){
		this(construtora.getId(),
				construtora.getNome(),
				construtora.getCnpj(),
				construtora.getTelefone(),
				construtora.getEmail(),
				construtora.isStatus());
		}
}

package br.com.magnasistemas.construcaocivil.dto.profissional;

import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Profissional;

public record DadosDetalhamentoProfissional(
		Long id,
		Construtora construtora,
		String cpf,
		String nome,
		String telefone,
		Cargo cargo,
		boolean status) {
	
	public DadosDetalhamentoProfissional (Profissional profissional) {
		this(profissional.getId(),
				profissional.getConstrutora(),
				profissional.getCpf(),
				profissional.getNome(),
				profissional.getTelefone(),
				profissional.getCargo(),
				profissional.isStatus());
	}

}

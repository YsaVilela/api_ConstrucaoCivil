package br.com.magnasistemas.construcaocivil.dto.projeto;

import br.com.magnasistemas.construcaocivil.entity.Endereco;
import br.com.magnasistemas.construcaocivil.entity.Projeto;
import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;

public record DadosDetalhamentoEndereco(
		Long id,
		String cep,
		String logradouro,
		Long numero,
		String complemento,
		Cidade cidade,
		Projeto projeto) {
	
	public DadosDetalhamentoEndereco(Endereco endereco){
		this(endereco.getId(),
				endereco.getCep(),
				endereco.getLogradouro(),
				endereco.getNumero(),
				endereco.getComplemento(),
				endereco.getCidade(),
				endereco.getProjeto());
	}

}

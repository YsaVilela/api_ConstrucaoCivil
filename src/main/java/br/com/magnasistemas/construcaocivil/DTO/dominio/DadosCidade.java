package br.com.magnasistemas.construcaocivil.dto.dominio;

import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
import br.com.magnasistemas.construcaocivil.entity.dominio.Estados;

public record DadosCidade (
	Long id,
	Estados estado,
	String nome)
{

	public DadosCidade(Cidade cidade) {
		this(cidade.getId(),
				cidade.getEstado(),
				cidade.getNome());
		
	}
}



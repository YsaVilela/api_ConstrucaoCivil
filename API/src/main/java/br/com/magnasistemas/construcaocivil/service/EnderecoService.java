package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoEndereco;
import br.com.magnasistemas.construcaocivil.entity.Endereco;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Page<DadosDetalhamentoEndereco> listar(Pageable paginacao) {
        return enderecoRepository.findAll(paginacao).map(DadosDetalhamentoEndereco::new);
	}

	public Optional<DadosDetalhamentoEndereco> buscarPorId(Long id) {
		Optional<Endereco> validarEndereco = enderecoRepository.findById(id);
		if (validarEndereco.isEmpty()) 
			throw new InvalidContentException ("Endereco não encontrado");
        return enderecoRepository.findById(id).map(DadosDetalhamentoEndereco::new); 
	}

	public Optional<DadosDetalhamentoEndereco> buscarPorIdProjeto(Long id) {
		Optional<Endereco> validarEndereco = enderecoRepository.findByIdProjeto(id); 
		if (validarEndereco.isEmpty()) 
			throw new InvalidContentException ("Endereco não encontrado");
        return enderecoRepository.findByIdProjeto(id).map(DadosDetalhamentoEndereco::new); 
	}
}

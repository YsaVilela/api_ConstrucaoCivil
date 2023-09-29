package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosAtualizarConstrutora;
import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import jakarta.validation.Valid;

@Service
public class ConstrutoraService {
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	public void criarConstrutora(DadosConstrutora dados) {
		Construtora construtora = new Construtora();
		construtora.setCnpj(dados.cnpj());
		construtora.setNome(dados.nome());
		construtora.setTelefone(dados.telefone());
		construtora.setEmail(dados.email());
		construtora.setStatus(true);
		construtoraRepository.save(construtora);
	}

	public Optional<DadosDetalhamentoConstrutora> buscarPorId(Long id) {
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(id);
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		
        return construtoraRepository.findById(id).map(DadosDetalhamentoConstrutora::new); 
	}

	public Page<DadosDetalhamentoConstrutora> listar(Pageable paginacao) {
        return construtoraRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoConstrutora::new);
	} 
	
	public Page<DadosDetalhamentoConstrutora> listarTodos(Pageable paginacao) {
        return construtoraRepository.findAll(paginacao).map(DadosDetalhamentoConstrutora::new);
	}

	public DadosDetalhamentoConstrutora atualizar(@Valid DadosAtualizarConstrutora dados) {
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(dados.id());
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		
		Construtora construtora = construtoraRepository.getReferenceById(dados.id());
				construtora.setNome(dados.nome());
				construtora.setTelefone(dados.telefone());
				construtora.setEmail(dados.email());
		construtoraRepository.save(construtora);
		return new DadosDetalhamentoConstrutora(construtora);
	}

	public DadosDetalhamentoConstrutora desativar(Long id) {
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(id);
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		
		Construtora construtora = construtoraRepository.getReferenceById(id);
			construtora.setStatus(false);
		construtoraRepository.save(construtora);
		return new DadosDetalhamentoConstrutora(construtora);
	}

	public void deletar(Long id) {
		construtoraRepository.deleteById(id);
	}
 
} 
	 
	



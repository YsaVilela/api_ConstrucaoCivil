package br.com.magnasistemas.construcaocivil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.construtora.DadosAtualizarConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.construtora.ValidadorConstrutora;
import jakarta.validation.Valid;

@Service
public class ConstrutoraService {
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private List<ValidadorConstrutora> validadoresConstrutora;

	
	public Optional<DadosDetalhamentoConstrutora> criarConstrutora(DadosConstrutora dados) {
		Construtora construtora = new Construtora();
		construtora.setCnpj(dados.cnpj());
		construtora.setNome(dados.nome());
		construtora.setTelefone(dados.telefone());
		construtora.setEmail(dados.email());
		construtora.setStatus(true);
		construtoraRepository.save(construtora);
		
		return construtoraRepository.findById(construtora.getId()).map(DadosDetalhamentoConstrutora::new);
	}

	public Optional<DadosDetalhamentoConstrutora> buscarPorId(Long id) {
		validadoresConstrutora.forEach(v -> v.validar(id));
        return construtoraRepository.findById(id).map(DadosDetalhamentoConstrutora::new); 
	}

	public Page<DadosDetalhamentoConstrutora> listar(Pageable paginacao) {
        return construtoraRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoConstrutora::new);
	} 
	
	public Page<DadosDetalhamentoConstrutora> listarTodos(Pageable paginacao) {
        return construtoraRepository.findAll(paginacao).map(DadosDetalhamentoConstrutora::new);
	}

	public DadosDetalhamentoConstrutora atualizar(@Valid DadosAtualizarConstrutora dados) {
		validadoresConstrutora.forEach(v -> v.validar(dados.id()));
		
		Construtora construtora = construtoraRepository.getReferenceById(dados.id());
				construtora.setNome(dados.nome()); 
				construtora.setTelefone(dados.telefone());
				construtora.setEmail(dados.email());
		construtoraRepository.save(construtora);
		return new DadosDetalhamentoConstrutora(construtora);
	}

	public DadosDetalhamentoConstrutora desativar(Long id) {
		validadoresConstrutora.forEach(v -> v.validar(id));
		Construtora construtora = construtoraRepository.getReferenceById(id);
			construtora.setStatus(false);
		construtoraRepository.save(construtora);
		
		profissionalService.construtoraDesativada(id);
		equipeService.construtoraDesativada(id); 
		return new DadosDetalhamentoConstrutora(construtora);
	}
	
	public DadosDetalhamentoConstrutora ativar(Long id) {
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(id);
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		
		Construtora construtora = construtoraRepository.getReferenceById(id);
			construtora.setStatus(true);
		construtoraRepository.save(construtora);
		
		profissionalService.construtoraAtivada(id);
		equipeService.construtoraAtivada(id);
		return new DadosDetalhamentoConstrutora(construtora);
	}

	public void deletar(Long id) {
		profissionalService.deleteByIdConstrutora(id);
		equipeService.deleteByIdConstrutora(id);
		projetoService.deleteByIdConstrutora(id);
		construtoraRepository.deleteById(id);		
	}

 
} 
	 
	



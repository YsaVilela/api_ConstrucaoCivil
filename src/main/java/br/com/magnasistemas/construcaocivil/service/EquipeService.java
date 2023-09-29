package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosAtualizarEquipe;
import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativada;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import jakarta.validation.Valid;

@Service
public class EquipeService {
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	public void criarEquipe(DadosEquipe dados) {
		Equipe equipe = new Equipe();
		
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(dados.idConstrutora());
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		Construtora construtora = construtoraRepository.getReferenceById(dados.idConstrutora());
		if (!construtora.isStatus())
			throw new EntidadeDesativada ("Construtora desativada");
				
		
		equipe.setConstrutora(construtora);
		equipe.setNome(dados.nome());
		equipe.setTurno(dados.turno());
		equipe.setStatus(true);
		
		equipeRepository.save(equipe);
	}

	public Optional<DadosDetalhamentoEquipe> buscarPorId(Long id) {
		Optional<Equipe> validarProfissional = equipeRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Equipe não encontrado");
		
        return equipeRepository.findById(id).map(DadosDetalhamentoEquipe::new); 
	}

	public Page<DadosDetalhamentoEquipe> listar(Pageable paginacao) {
        return equipeRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoEquipe::new);
	} 
	
	public Page<DadosDetalhamentoEquipe> listarTodos(Pageable paginacao) {
        return equipeRepository.findAll(paginacao).map(DadosDetalhamentoEquipe::new);
	}

	public DadosDetalhamentoEquipe atualizar(@Valid DadosAtualizarEquipe dados) {
		Optional<Equipe> validarEquipe = equipeRepository.findById(dados.id());
		if (validarEquipe.isEmpty()) 
			throw new BuscarException ("Equipe não encontrado");
		
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(dados.idConstrutora());
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		Construtora construtora = construtoraRepository.getReferenceById(dados.idConstrutora());
		if (!construtora.isStatus())
			throw new EntidadeDesativada ("Construtora desativada");
		
		Equipe equipe = equipeRepository.getReferenceById(dados.id());
			equipe.setConstrutora(construtora);
			equipe.setNome(dados.nome());
			equipe.setTurno(dados.turno());
			equipeRepository.save(equipe);
		return new DadosDetalhamentoEquipe(equipe);
	}

	public DadosDetalhamentoEquipe desativar(Long id) {
		Optional<Equipe> validarEquipe = equipeRepository.findById(id);
		if (validarEquipe.isEmpty()) 
			throw new BuscarException ("Equipe não encontrado");
		
		Equipe equipe = equipeRepository.getReferenceById(id);
			equipe.setStatus(false);
			equipeRepository.save(equipe);
		return new DadosDetalhamentoEquipe(equipe);
	}

	public void deletar(Long id) {
		equipeRepository.deleteById(id);
	}
 

}

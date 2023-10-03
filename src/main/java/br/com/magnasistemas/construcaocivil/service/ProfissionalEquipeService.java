package br.com.magnasistemas.construcaocivil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosDetalhamentoProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.entity.ProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalEquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.equipe.ValidadorEquipe;
import br.com.magnasistemas.construcaocivil.service.validacoes.profissional.ValidadorProfissional;

@Service
public class ProfissionalEquipeService {

	@Autowired
	private ProfissionalEquipeRepository profissionalEquipeRepository;

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private List<ValidadorProfissional> validadoresProfissional;
	
	@Autowired
	private List<ValidadorEquipe> validadoresEquipe;
	

	public Optional<DadosDetalhamentoProfissionalEquipe> criarProfissioanlEquipe(DadosProfissionalEquipe dados) {
		ProfissionalEquipe profissionalEquipe = new ProfissionalEquipe();

		validadoresEquipe.forEach(v -> v.validar(dados.idEquipe()));
		validadoresProfissional.forEach(v -> v.validar(dados.idProfissional()));	

		profissionalEquipe.setEquipe(equipeRepository.getReferenceById(dados.idEquipe()));
		profissionalEquipe.setProfissional(profissionalRepository.getReferenceById(dados.idProfissional()));
		profissionalEquipeRepository.save(profissionalEquipe);
		
		return profissionalEquipeRepository.findById(profissionalEquipe.getId()).map(DadosDetalhamentoProfissionalEquipe::new);
		
	}

	public Page<DadosDetalhamentoProfissionalEquipe> listarEquipe( Long id,Pageable paginacao) {
		validadoresEquipe.forEach(v -> v.validar(id));
		return profissionalEquipeRepository.findByIdEquipe(id, paginacao).map(DadosDetalhamentoProfissionalEquipe::new);
	}

	public Page<DadosDetalhamentoProfissionalEquipe> listarProfissional(Long id, Pageable paginacao) {
		validadoresProfissional.forEach(v -> v.validar(id));	
		return profissionalEquipeRepository.findByIdProfissional(id,paginacao).map(DadosDetalhamentoProfissionalEquipe::new);
	}

	public Page<DadosDetalhamentoProfissionalEquipe> listar(Pageable paginacao) {
		return profissionalEquipeRepository.findAll(paginacao).map(DadosDetalhamentoProfissionalEquipe::new);
	}
	
	public void deletar(Long id) {
		profissionalEquipeRepository.deleteById(id);
	}
	
}

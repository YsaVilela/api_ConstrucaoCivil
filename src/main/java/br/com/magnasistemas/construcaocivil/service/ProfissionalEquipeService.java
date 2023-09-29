package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.DTO.profissionalEquipe.DadosDetalhamentoProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.DTO.profissionalEquipe.DadosProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.entity.ProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativada;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalEquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@Service
public class ProfissionalEquipeService {
	
	@Autowired
	private ProfissionalEquipeRepository profissionalEquipeRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	public void criarProfissioanlEquipe(DadosProfissionalEquipe dados) {
		ProfissionalEquipe profissionalEquipe = new ProfissionalEquipe();
		
		Optional<Equipe> validarEquipe = equipeRepository.findById(dados.idEquipe());
		if (validarEquipe.isEmpty()) 
			throw new BuscarException ("Equipe não encontrada");
		Equipe equipe = equipeRepository.getReferenceById(dados.idEquipe());
		if (!equipe.isStatus())
			throw new EntidadeDesativada ("Equipe desativada");
		
		Optional<Profissional> validarProfissional = profissionalRepository.findById(dados.idProfissional());
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Profissional não encontrado");
		Profissional profissional = profissionalRepository.getReferenceById(dados.idProfissional());
		if (!equipe.isStatus())
			throw new EntidadeDesativada ("Equipe desativada");
		
		profissionalEquipe.setEquipe(equipe);
		profissionalEquipe.setProfissional(profissional);
		profissionalEquipeRepository.save(profissionalEquipe);
	}
	
	public Page<DadosDetalhamentoProfissionalEquipe> listarEquipe(Pageable paginacao, Long id) {
		Optional<Equipe> validarEquipe = equipeRepository.findById(id);
		if (validarEquipe.isEmpty()) 
			throw new BuscarException ("Equipe não encontrada");
		Equipe equipe = equipeRepository.getReferenceById(id);
		if (!equipe.isStatus())
			throw new EntidadeDesativada ("Equipe desativada");
			 
        return profissionalEquipeRepository.findByIdEquipe(
        		equipe.getId(), paginacao).map(DadosDetalhamentoProfissionalEquipe::new);
	} 
	
	public Page<DadosDetalhamentoProfissionalEquipe> listarProfissional(Pageable paginacao, Long id) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Profissional não encontrado");
		Profissional profissional = profissionalRepository.getReferenceById(id);
		if (!profissional.isStatus())
			throw new EntidadeDesativada ("Profissional desativado");
			
        return profissionalEquipeRepository.findByIdEquipe(
        		profissional.getId(),paginacao).map(DadosDetalhamentoProfissionalEquipe::new);
	}


	
	
 
}

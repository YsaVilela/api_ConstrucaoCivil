package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosAtualizarProfissional;
import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativada;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
import jakarta.validation.Valid;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public void criarProfissional(DadosProfissional dados) {
		Profissional profissional = new Profissional();
		
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(dados.idConstrutora());
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		Construtora construtora = construtoraRepository.getReferenceById(dados.idConstrutora());
		if (!construtora.isStatus())
			throw new EntidadeDesativada ("Construtora desativada");
				
		Optional<Cargo> validarCargo = cargoRepository.findById(dados.idCargo());
		if (validarCargo.isEmpty()) 
			throw new BuscarException ("Cargo não encontrado");
		
		profissional.setConstrutora(construtora);
		profissional.setCpf(dados.cpf());
		profissional.setNome(dados.nome());
		profissional.setTelefone(dados.telefone());
		profissional.setCargo(cargoRepository.getReferenceById(dados.idCargo()));
		profissional.setStatus(true);
		
		profissionalRepository.save(profissional);
	}

	public Optional<DadosDetalhamentoProfissional> buscarPorId(Long id) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Profissional não encontrado");
		
        return profissionalRepository.findById(id).map(DadosDetalhamentoProfissional::new); 
	}

	public Page<DadosDetalhamentoProfissional> listar(Pageable paginacao) {
        return profissionalRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoProfissional::new);
	} 
	
	public Page<DadosDetalhamentoProfissional> listarTodos(Pageable paginacao) {
        return profissionalRepository.findAll(paginacao).map(DadosDetalhamentoProfissional::new);
	}

	public DadosDetalhamentoProfissional atualizar(@Valid DadosAtualizarProfissional dados) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(dados.id());
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Profissional não encontrado");
		
		Optional<Cargo> validarCargo = cargoRepository.findById(dados.idCargo());
		if (validarCargo.isEmpty()) 
			throw new BuscarException ("Cargo não encontrado");
		
		Optional<Construtora> validarConstrutora = construtoraRepository.findById(dados.idConstrutora());
		if (validarConstrutora.isEmpty()) 
			throw new BuscarException ("Construtora não encontrada");
		Construtora construtora = construtoraRepository.getReferenceById(dados.idConstrutora());
		if (!construtora.isStatus())
			throw new EntidadeDesativada ("Construtora desativada");
		
		Profissional profissional = profissionalRepository.getReferenceById(dados.id());
			profissional.setConstrutora(construtora);
			profissional.setNome(dados.nome());
			profissional.setTelefone(dados.telefone());
			profissional.setCargo(cargoRepository.getReferenceById(dados.idCargo()));
		profissionalRepository.save(profissional);
		return new DadosDetalhamentoProfissional(profissional);
	}

	public DadosDetalhamentoProfissional desativar(Long id) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(id);
		if (validarProfissional.isEmpty()) 
			throw new BuscarException ("Profissional não encontrado");
		
		Profissional profissional = profissionalRepository.getReferenceById(id);
			profissional.setStatus(false);
			profissionalRepository.save(profissional);
		return new DadosDetalhamentoProfissional(profissional);
	}

	public void deletar(Long id) {
		profissionalRepository.deleteById(id);
	}
 

}

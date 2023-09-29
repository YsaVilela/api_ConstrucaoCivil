package br.com.magnasistemas.construcaocivil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.DTO.cargo.DadosAtualizarCargo;
import br.com.magnasistemas.construcaocivil.DTO.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.DTO.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;
import jakarta.validation.Valid;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public void criarCargo(DadosCargo dados) {
		Cargo cargo = new Cargo();
		cargo.setNome(dados.nome());
		cargo.setRemuneracao(dados.remuneracao());
		cargoRepository.save(cargo);
	}
	
	public Optional<DadosDetalhamentoCargo> buscarPorId(Long id) {
		Optional<Cargo> validarCargo = cargoRepository.findById(id);
		if (validarCargo.isEmpty()) 
			throw new BuscarException ("Cargo não encontrado");
		
        return cargoRepository.findById(id).map(DadosDetalhamentoCargo::new); 
	}
	
	public Page<DadosDetalhamentoCargo> listar(Pageable paginacao) {
        return cargoRepository.findAll(paginacao).map(DadosDetalhamentoCargo::new);
	}
	  
	public DadosDetalhamentoCargo atualizar(@Valid DadosAtualizarCargo dados) {
		Optional<Cargo> validarCargo = cargoRepository.findById(dados.id());
		if (validarCargo.isEmpty()) 
			throw new BuscarException ("Cargo não encontrado");
		
		Cargo cargo = cargoRepository.getReferenceById(dados.id());
				cargo.setNome(dados.nome());
				cargo.setRemuneracao(dados.remuneracao());
			cargoRepository.save(cargo);
		return new DadosDetalhamentoCargo(cargo);
	}
	
	public void deletar(Long id) {
		cargoRepository.deleteById(id);
	}
	

}

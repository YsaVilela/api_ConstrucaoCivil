package br.com.magnasistemas.construcaocivil.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosAtualizarCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.entity.Cargo;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.exception.CustomDataIntegrityException;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.cargo.remuneracao.ValidadorRemuneracaoCargo;
import jakarta.validation.Valid;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private List<ValidadorRemuneracaoCargo> validadoresRemuneracaoCargo;

	public Optional<DadosDetalhamentoCargo> criarCargo(DadosCargo dados) {

		if (cargoRepository.findByNome(dados.nome()) != null)
			throw new CustomDataIntegrityException("Duplicação de valor da chave nome viola a restrição de unicidade.");

		Cargo cargo = new Cargo();
		cargo.setNome(dados.nome());
		validadoresRemuneracaoCargo.forEach(r -> r.validar(cargo, dados.remuneracao()));
		cargo.setRemuneracao(dados.remuneracao());
		cargoRepository.save(cargo);
		return cargoRepository.findById(cargo.getId()).map(DadosDetalhamentoCargo::new);

	}

	public Optional<DadosDetalhamentoCargo> buscarPorId(Long id) {
		Optional<Cargo> validarCargo = cargoRepository.findById(id);
		if (validarCargo.isEmpty())
			throw new InvalidContentException("Cargo não encontrado");

		return cargoRepository.findById(id).map(DadosDetalhamentoCargo::new);
	}

	public Page<DadosDetalhamentoCargo> listar(Pageable paginacao) {
		return cargoRepository.findAll(paginacao).map(DadosDetalhamentoCargo::new);
	}

	public DadosDetalhamentoCargo atualizar(@Valid DadosAtualizarCargo dados) {
		Optional<Cargo> validarCargo = cargoRepository.findById(dados.id());
		if (validarCargo.isEmpty())
			throw new InvalidContentException("Cargo não encontrado");
		if (cargoRepository.findByNome(dados.nome()) != null)
			throw new CustomDataIntegrityException("Duplicação de valor da chave nome viola a restrição de unicidade.");

		Cargo cargo = cargoRepository.getReferenceById(dados.id());
		validadoresRemuneracaoCargo.forEach(r -> r.validar(cargo, dados.remuneracao()));
		cargo.setNome(dados.nome());
		cargo.setRemuneracao(dados.remuneracao());
		cargoRepository.save(cargo);
		return new DadosDetalhamentoCargo(cargo);

	}

	public void deletar(Long id) {
		cargoRepository.deleteById(id);
	}

}

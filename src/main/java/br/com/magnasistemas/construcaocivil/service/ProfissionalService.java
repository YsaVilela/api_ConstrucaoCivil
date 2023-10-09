package br.com.magnasistemas.construcaocivil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.profissional.DadosAtualizarProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.exception.CustomDataIntegrityException;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalEquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.cargo.ValidadorCargo;
import br.com.magnasistemas.construcaocivil.service.validacoes.construtora.ValidadorConstrutora;
import br.com.magnasistemas.construcaocivil.service.validacoes.profissional.ValidadorProfissional;
import jakarta.validation.Valid;

@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private ProfissionalEquipeRepository profissionalEquipeRepository;

	@Autowired
	private List<ValidadorConstrutora> validadoresConstrutora;

	@Autowired
	private List<ValidadorCargo> validadoresCargo;

	@Autowired
	private List<ValidadorProfissional> validadoresProfissional;
	
	void validacao(String cpf, String telefone) {
		if (profissionalRepository.findByCpf(cpf) != null)
			throw new CustomDataIntegrityException("Duplicação de valor da chave cpf viola a restrição de unicidade.");
		if (profissionalRepository.findByTelefone(telefone)!= null)
			throw new CustomDataIntegrityException("Duplicação de valor da chave telefone viola a restrição de unicidade.");
	}

	public Optional<DadosDetalhamentoProfissional> criarProfissional(DadosProfissional dados) {
		Profissional profissional = new Profissional();

		validadoresConstrutora.forEach(v -> v.validar(dados.idConstrutora()));
		validadoresCargo.forEach(v -> v.validar(dados.idCargo()));
		validacao(dados.cpf(), dados.telefone());

		profissional.setConstrutora(construtoraRepository.getReferenceById(dados.idConstrutora()));
		profissional.setCpf(dados.cpf());
		profissional.setNome(dados.nome());
		profissional.setTelefone(dados.telefone());
		profissional.setCargo(cargoRepository.getReferenceById(dados.idCargo()));
		profissional.setStatus(true);

		profissionalRepository.save(profissional);

		return profissionalRepository.findById(profissional.getId()).map(DadosDetalhamentoProfissional::new);
	}

	public Optional<DadosDetalhamentoProfissional> buscarPorId(Long id) {
		validadoresProfissional.forEach(v -> v.validar(id));
		return profissionalRepository.findById(id).map(DadosDetalhamentoProfissional::new);
	}

	public Page<DadosDetalhamentoProfissional> listar(Pageable paginacao) {
		return profissionalRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoProfissional::new);
	}

	public Page<DadosDetalhamentoProfissional> listarTodos(Pageable paginacao) {
		return profissionalRepository.findAll(paginacao).map(DadosDetalhamentoProfissional::new);
	}

	public DadosDetalhamentoProfissional atualizar(@Valid DadosAtualizarProfissional dados) {
		validadoresProfissional.forEach(v -> v.validar(dados.id()));
		validadoresCargo.forEach(v -> v.validar(dados.idCargo()));
		validadoresConstrutora.forEach(v -> v.validar(dados.idConstrutora()));
		validacao(dados.cpf(), dados.telefone());

		Profissional profissional = profissionalRepository.getReferenceById(dados.id());
		profissional.setConstrutora(construtoraRepository.getReferenceById(dados.id()));
		profissional.setNome(dados.nome());
		profissional.setTelefone(dados.telefone());
		profissional.setCargo(cargoRepository.getReferenceById(dados.idCargo()));
		profissionalRepository.save(profissional);
		return new DadosDetalhamentoProfissional(profissional);
	}

	public DadosDetalhamentoProfissional ativar(Long id) {
		Optional<Profissional> validarProfissional = profissionalRepository.findById(id);
		if (validarProfissional.isEmpty())
			throw new InvalidContentException("Profissional não encontrado");

		Profissional profissional = profissionalRepository.getReferenceById(id);
		profissional.setStatus(true);
		profissionalRepository.save(profissional);
		return new DadosDetalhamentoProfissional(profissional);
	}

	public DadosDetalhamentoProfissional desativar(Long id) {
		validadoresProfissional.forEach(v -> v.validar(id));

		Profissional profissional = profissionalRepository.getReferenceById(id);
		profissional.setStatus(false);
		profissionalRepository.save(profissional);
		return new DadosDetalhamentoProfissional(profissional);
	}

	public void deletar(Long id) {
		profissionalRepository.deleteById(id);
		profissionalEquipeRepository.deleteByIdProfissional(id);
	}

	public void deleteByIdConstrutora(Long idConstrutora) {
		profissionalEquipeRepository.deleteByIdProfissionalConstrutora(idConstrutora);
		profissionalRepository.deleteByIdConstrutora(idConstrutora);
	}

	public void construtoraDesativada(Long id) {
		Profissional profissional = profissionalRepository.findByIdConstrutora(id);
		if (profissional != null) {
			profissional.setStatus(false);
			profissionalRepository.save(profissional);
		}
	}

	public void construtoraAtivada(Long id) {
		Profissional profissional = profissionalRepository.findByIdConstrutora(id);
		if (profissional != null) {
			profissional.setStatus(true);
			profissionalRepository.save(profissional);
		}
	}

}

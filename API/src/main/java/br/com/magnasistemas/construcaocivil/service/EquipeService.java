package br.com.magnasistemas.construcaocivil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.equipe.DadosAtualizarEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.exception.InvalidContentException;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalEquipeRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.construtora.ValidadorConstrutora;
import br.com.magnasistemas.construcaocivil.service.validacoes.equipe.ValidadorEquipe;
import jakarta.validation.Valid;

@Service
public class EquipeService {
	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Autowired
	private ProfissionalEquipeRepository profissionalEquipeRepository;

	@Autowired
	private List<ValidadorConstrutora> validadoresConstrutora;

	@Autowired
	private List<ValidadorEquipe> validadoresEquipe;

	public Optional<DadosDetalhamentoEquipe> criarEquipe(DadosEquipe dados) {

		validadoresConstrutora.forEach(v -> v.validar(dados.idConstrutora()));

		Equipe equipe = new Equipe();
		equipe.setConstrutora(construtoraRepository.getReferenceById(dados.idConstrutora()));
		equipe.setNome(dados.nome());
		equipe.setTurno(dados.turno());
		equipe.setStatus(true);

		equipeRepository.save(equipe);

		return equipeRepository.findById(equipe.getId()).map(DadosDetalhamentoEquipe::new);
	}

	public Optional<DadosDetalhamentoEquipe> buscarPorId(Long id) {

		validadoresEquipe.forEach(v -> v.validar(id));

		return equipeRepository.findById(id).map(DadosDetalhamentoEquipe::new);
	}

	public Page<DadosDetalhamentoEquipe> listar(Pageable paginacao) {
		return equipeRepository.findAllByStatusTrue(paginacao).map(DadosDetalhamentoEquipe::new);
	}

	public Page<DadosDetalhamentoEquipe> listarTodos(Pageable paginacao) {
		return equipeRepository.findAll(paginacao).map(DadosDetalhamentoEquipe::new);
	}

	public DadosDetalhamentoEquipe atualizar(@Valid DadosAtualizarEquipe dados) {

		validadoresEquipe.forEach(v -> v.validar(dados.id()));
		validadoresConstrutora.forEach(v -> v.validar(dados.idConstrutora()));

		Equipe equipe = equipeRepository.getReferenceById(dados.id());
		equipe.setConstrutora(construtoraRepository.getReferenceById(dados.id()));
		equipe.setNome(dados.nome());
		equipe.setTurno(dados.turno());
		equipeRepository.save(equipe);
		return new DadosDetalhamentoEquipe(equipe);
	}

	public DadosDetalhamentoEquipe ativar(Long id) {
		Optional<Equipe> validarEquipe = equipeRepository.findById(id);
		if (validarEquipe.isEmpty())
			throw new InvalidContentException("Equipe não encontrado");

		Equipe equipe = equipeRepository.getReferenceById(id);
		equipe.setStatus(true);
		equipeRepository.save(equipe);
		return new DadosDetalhamentoEquipe(equipe);
	}

	public DadosDetalhamentoEquipe desativar(Long id) {
		validadoresEquipe.forEach(v -> v.validar(id));

		Equipe equipe = equipeRepository.getReferenceById(id);
		equipe.setStatus(false);
		equipeRepository.save(equipe);
		return new DadosDetalhamentoEquipe(equipe);
	}

	public void deletar(Long id) {
		profissionalEquipeRepository.deleteByIdEquipe(id);
		equipeRepository.deleteById(id);
	}

	public void deleteByIdConstrutora(Long idConstrutora) {
		profissionalEquipeRepository.deleteByIdEquipeConstrutora(idConstrutora);
		equipeRepository.deleteByIdConstrutora(idConstrutora);
	}

	public void construtoraDesativada(Long id) {
		Equipe equipe = equipeRepository.findByIdConstrutora(id);
		if (equipe != null) {
			equipe.setStatus(false);
			equipeRepository.save(equipe);
		}

	}

	public void construtoraAtivada(Long id) {
		Equipe equipe = equipeRepository.findByIdConstrutora(id);
		if (equipe != null) {
			equipe.setStatus(true);
			equipeRepository.save(equipe);
		}
	}

}

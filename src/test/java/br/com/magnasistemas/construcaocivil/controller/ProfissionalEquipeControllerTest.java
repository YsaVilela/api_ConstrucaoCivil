package br.com.magnasistemas.construcaocivil.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosDetalhamentoProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalEquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class ProfissionalEquipeControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProfissionalEquipeRepository profissionalEquipeRepository;

	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private EquipeRepository equipeRepository;

	void iniciarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
	}

	void iniciarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 1600.45);
		restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, DadosDetalhamentoCargo.class);
	}

	void iniciarProfissional() {
		DadosProfissional dadosProfissional = new DadosProfissional(1L, "12345678901", "Profissional Teste ",
				"11912345678", 1l);
		restTemplate.postForEntity("/profissional/cadastrar", dadosProfissional, DadosDetalhamentoProfissional.class);
	}

	void iniciarEquipe() {
		DadosEquipe dadosEquipe = new DadosEquipe(1L, "Equipe teste", Turno.NOTURNO);
		restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe, DadosDetalhamentoEquipe.class);
	}

	void iniciarProfissionalEquipe() {
		DadosProfissionalEquipe dadosProfissionalEquipe = new DadosProfissionalEquipe(1L, 1l);
		restTemplate.postForEntity("/profissional/equipe/cadastrar", dadosProfissionalEquipe,
				DadosDetalhamentoProfissionalEquipe.class);
	}

	@BeforeEach
	void iniciar() {
		iniciarConstrutora();
		iniciarCargo();
		iniciarProfissional();
		iniciarEquipe();
	}

	@AfterEach
	void finlizar() {
		profissionalEquipeRepository.deleteAllAndResetSequence();
		equipeRepository.deleteAllAndResetSequence();
		profissionalRepository.deleteAllAndResetSequence();
		construtoraRepository.deleteAllAndResetSequence();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarProfissionalEquipe() {

		DadosProfissionalEquipe dadosProfissionalEquipe = new DadosProfissionalEquipe(1L, 1l);

		ResponseEntity<DadosDetalhamentoProfissionalEquipe> response = restTemplate.postForEntity(
				"/profissional/equipe/cadastrar", dadosProfissionalEquipe, DadosDetalhamentoProfissionalEquipe.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um erro quando tenatr adicionar um profissional em uma equipe que não pertence a mesma construtora")
	void criarProfissionalEquipeConstrutoraDiferentes() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901235", "Construtora Teste", "11912345679",
				"testeteste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);

		DadosEquipe dadosEquipe = new DadosEquipe(2L, "Equipe teste", Turno.NOTURNO);
		restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe, DadosDetalhamentoEquipe.class);

		DadosProfissionalEquipe dadosProfissionalEquipe = new DadosProfissionalEquipe(2L, 1L);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/profissional/equipe/cadastrar",
				dadosProfissionalEquipe, JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criado com profissional desativado")
	void criarProfissionalProfissionalDesativado() {

		restTemplate.exchange("/profissional/desativar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoProfissional.class);

		DadosProfissionalEquipe dadosProfissionalEquipe = new DadosProfissionalEquipe(1L, 1l);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/profissional/equipe/cadastrar",
				dadosProfissionalEquipe, JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("Deve retornar um erro quando criado com equipe desativada")
	void criarProfissionalEquipeDesativada() {

		restTemplate.exchange("/equipe/desativar/1", HttpMethod.DELETE, null, DadosDetalhamentoProfissional.class);

		DadosProfissionalEquipe dadosProfissionalEquipe = new DadosProfissionalEquipe(1L, 1l);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("/profissional/equipe/cadastrar",
				dadosProfissionalEquipe, JsonNode.class);

		assertTrue(response.getStatusCode().is4xxClientError());

	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar inserções de equipes por id")
	void listarProfissionalEquipePorId() {
		iniciarProfissionalEquipe();

		ResponseEntity<DadosDetalhamentoProfissionalEquipe> response = restTemplate
				.getForEntity("/profissional/equipe/buscarEquipe/1", DadosDetalhamentoProfissionalEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar inserções de um profissional por id")
	void listarProfissionais() {
		iniciarProfissionalEquipe();

		ResponseEntity<DadosDetalhamentoProfissionalEquipe> response = restTemplate
				.getForEntity("/profissional/equipe/buscarProfissional/1", DadosDetalhamentoProfissionalEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar as inserções")
	void listarProfissionaisEquipes() {
		iniciarProfissionalEquipe();

		ResponseEntity<DadosDetalhamentoProfissionalEquipe> response = restTemplate
				.getForEntity("/profissional/equipe/listar", DadosDetalhamentoProfissionalEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar")
	void deletarEquipe() {
		iniciarProfissionalEquipe();

		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.exchange("/profissional/equipe/deletar/1",
				HttpMethod.DELETE, null, DadosDetalhamentoEquipe.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}

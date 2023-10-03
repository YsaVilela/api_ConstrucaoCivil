package br.com.magnasistemas.construcaocivil.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosAtualizarCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosAtualizarConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosAtualizarEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
import br.com.magnasistemas.construcaocivil.repository.ProjetoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class EquipeControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;

	
	void iniciarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
	}
	
	void iniciarEquipe() {
		DadosEquipe dadosEquipe = new DadosEquipe(1L, "Equipe teste", Turno.NOTURNO);
		restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe,
				DadosDetalhamentoEquipe.class);
	}
	
	
	@BeforeEach
	void iniciar() { 
		iniciarConstrutora();
	}
	
	@AfterEach
	void finlizar(){
		equipeRepository.deleteAllAndResetSequence();
		projetoRepository.deleteAllAndResetSequence();
		construtoraRepository.deleteAllAndResetSequence();
	}

	
	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarEquipe() {

		DadosEquipe dadosEquipe = new DadosEquipe(1L, "Equipe teste", Turno.NOTURNO);
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe,
				DadosDetalhamentoEquipe.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@ParameterizedTest
	@MethodSource("provideInvalidEquipeData")
	@DisplayName("Deve retornar erro quando algum campo é nulo")
	void criarEquipeComCamposNulos(Long idConstrutora, String nome, Turno turno) {
		DadosEquipe dadosEquipe = new DadosEquipe(idConstrutora, nome, turno);
		ResponseEntity response = restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe, List.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

		private static Stream<Object[]> provideInvalidEquipeData() {
			return Stream.of(new Object[] { null, "Teste", Turno.NOTURNO},
					new Object[] { 1L, null, Turno.NOTURNO},
					new Object[] { 1L, "Teste", null});
		}
		
		
	@Test
	@DisplayName("deve devolver codigo http 200 quando listar uma equipe por id")
	void listarEquipePorId() {
		iniciarEquipe();
		
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.getForEntity("/equipe/buscar/1",
				DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	
	@Test
	@DisplayName("Deve retorna um erro quando listar uma equipe por um id inválido")
	void listarEquipePorIdInvalido() {
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.getForEntity("/equipe/buscar/1",
				DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	
	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar todas as equipes")
	void listarEquipes() {
		iniciarEquipe();
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.getForEntity("/equipe/listar/todos",
				DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	
	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as equipes ativas")
	void listarEquipesAtivas() {
		iniciarEquipe();
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.getForEntity("/equipe/listar",
				DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando atualizar uma equipe")
	void atualizarEquipe() {
		iniciarEquipe();
		DadosAtualizarEquipe dadosAtualizarEquipe = new DadosAtualizarEquipe(1L, 1L, "Equipe", Turno.MATUTINO);
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.exchange("/equipe/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarEquipe), DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarEquipeInvalida() {
		DadosAtualizarEquipe dadosAtualizarEquipe = new DadosAtualizarEquipe(1L, 1L, "Equipe", Turno.MATUTINO);

		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.exchange("/equipe/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarEquipe), DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}


	@Test
	@DisplayName("deve retornar um 204 quando desativar uma equipe com um id válido")
	void desativarEquipe() {
		iniciarEquipe();
		ResponseEntity response = restTemplate.exchange("/equipe/desativar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoEquipe.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	@DisplayName("Deve retornar codigo http 200 quando ativar uma equipe")
	void ativarEquipe() {
		iniciarEquipe();
		restTemplate.exchange("/equipe/desativar/1", HttpMethod.DELETE, null, DadosDetalhamentoEquipe.class);
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.exchange("/equipe/ativar/1", HttpMethod.PUT,
				null, DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	
	@Test
	@DisplayName("Deve retornar um erro quando ativar uma equipe com um id inválido")
	void ativarEquipeIdInvalido() {
 
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.exchange("/equipe/ativar/1", HttpMethod.PUT,
				null, DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar com um id válido")
	void deletarEquipe() {
		iniciarEquipe();
		ResponseEntity response = restTemplate.exchange("/equipe/deletar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoEquipe.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando cadastrar uma equipe com uma construtora desativada")
	void cadastarComConstrutoraInvalida() {
		restTemplate.exchange("/construtora/desativar/1", HttpMethod.DELETE, null, DadosDetalhamentoConstrutora.class);

		DadosEquipe dadosEquipe = new DadosEquipe(1L, "Equipe teste", Turno.NOTURNO);
		ResponseEntity<DadosDetalhamentoEquipe> response = restTemplate.postForEntity("/equipe/cadastrar", dadosEquipe,
				DadosDetalhamentoEquipe.class);

		assertTrue(response.getStatusCode().is5xxServerError()); 
	}

}

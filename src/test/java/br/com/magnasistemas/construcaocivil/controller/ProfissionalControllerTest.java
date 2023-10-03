package br.com.magnasistemas.construcaocivil.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosAtualizarProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.exception.TratarErros;
import br.com.magnasistemas.construcaocivil.exception.TratarErros.DadosErroValidacao;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class ProfissionalControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;

	void iniciarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
	}

	void iniciarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 123.45);
		restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, DadosDetalhamentoCargo.class);
	}
	
	void iniciarProfissional() {
		DadosProfissional dadosProfissional = new DadosProfissional(1L, "12345678901", "Profissional Teste ",
				"11912345678", 1l);
		restTemplate.postForEntity("/profissional/cadastrar", dadosProfissional, DadosDetalhamentoProfissional.class);
	}

	
	@BeforeEach
	void iniciar() {
		equipeRepository.deleteAllAndResetSequence();
		profissionalRepository.deleteAllAndResetSequence();
		cargoRepository.deleteAllAndResetSequence();
		construtoraRepository.deleteAllAndResetSequence();
		
		iniciarCargo();
		iniciarConstrutora();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarProfissional() {

		DadosProfissional dadosProfissional = new DadosProfissional(1L, "12345678901", "Profissional Teste ",
				"11912345678", 1l);

		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.postForEntity("/profissional/cadastrar",
				dadosProfissional, DadosDetalhamentoProfissional.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@ParameterizedTest
	@MethodSource("provideInvalidProfissionalData")
	@DisplayName("Deve retornar erro quando algum campo é nulo")
	void criarProfissionalComCamposNulos(Long idConstrutora, String cpf, String nome, String telefone, Long IdCargo) {
		DadosProfissional dadosProfissional = new DadosProfissional(idConstrutora, cpf, nome, telefone, IdCargo);
		ResponseEntity response = restTemplate.postForEntity("/profissional/cadastrar", dadosProfissional, List.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private static Stream<Object[]> provideInvalidProfissionalData() {
		return Stream.of(new Object[] { null, "12345678901", "ProfissionalTeste", "12345678901", 1L },
				new Object[] { 1L, null, "ProfissionalTeste", "12345678901", 1L },
				new Object[] { 1L, "12345678901", null, "12345678901", 1L },
				new Object[] { 1L, "12345678901", "ProfissionalTeste", null, 1L },
				new Object[] { 1L, "12345678901", "ProfissionalTeste", "12345678901", null });
	}
	
	@Test
	@DisplayName("Deve retornar erro quando criar profissional com cargo inválido")
	void criarProfissionalComCargoInvalido() {

		DadosProfissional dadosProfissional = new DadosProfissional(1L, "12345678901", "Profissional Teste ",
				"11912345678", 2l);
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.postForEntity("/profissional/cadastrar",
				dadosProfissional, DadosDetalhamentoProfissional.class);
		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar um profissional por id")
	void listarProfissionalPorId() {
		iniciarProfissional();
		
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.getForEntity("/profissional/buscar/1",
				DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}


	@Test
	@DisplayName("deve retornar um erro quando listar um profissional por um id inválido")
	void listarProfissionalPorIdInválido() {
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.getForEntity("/profissional/buscar/1",
				DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}
	

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar um profissional")
	void listarProfissional() {
		iniciarProfissional();		

		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.getForEntity("/profissional/listar/todos",
				DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as profissionais ativos")
	void listarProfissionaisAtivos() {
		iniciarProfissional();
		
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.getForEntity("/profissional/listar",
				DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	

	@Test
	@DisplayName("deve devolver codigo http 200 quando atualizar uma Profissional")
	void atualizarEquipe() {
		iniciarProfissional();

		DadosAtualizarProfissional dadosAtualizarProfissional = new DadosAtualizarProfissional(1L, 1L, "10987654321",
				"Profissional Teste", "11987654321", 1l);

		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.exchange("/profissional/atualizar",
				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarProfissional), DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	

	@Test
	@DisplayName("deve retornar um erro quando atualizar com um id inválido")
	void atualizarEquipeInvalida() {

		DadosAtualizarProfissional dadosAtualizarProfissional = new DadosAtualizarProfissional(2L, 1L, "12345678901",
				"ProfissionalTeste", "11987654321", 1l);

		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.exchange("/profissional/atualizar",
				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarProfissional), DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}
	

	@Test
	@DisplayName("deve retornar um 204 quando desativar uma profissional com um id válido")
	void desativarProfissional() {
		iniciarProfissional();

		ResponseEntity response = restTemplate.exchange("/profissional/desativar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoProfissional.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	
	@Test
	@DisplayName("deve devolver codigo http 200 quando ativar um profissional")
	void ativarEquipe() {
		iniciarProfissional();

		restTemplate.exchange("/equipe/desativar/1", HttpMethod.DELETE, null, DadosDetalhamentoProfissional.class);
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.exchange("/profissional/ativar/1", HttpMethod.PUT,
				null, DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	
	@Test
	@DisplayName("deve devolver um erro quando ativar um profissional com um id inválido")
	void ativarEquipeIdInvalido() {
		ResponseEntity<DadosDetalhamentoProfissional> response = restTemplate.exchange("/profissional/ativar/1", HttpMethod.PUT,
				null, DadosDetalhamentoProfissional.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	
	@Test
	@DisplayName("deve retornar um 204 quando deletar com um id válido")
	void deletarProfissional() {
		iniciarProfissional();

		ResponseEntity response = restTemplate.exchange("/profissional/deletar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoProfissional.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}

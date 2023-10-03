package br.com.magnasistemas.construcaocivil.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosAtualizarCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.repository.CargoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class CargoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CargoRepository cargoRepository;

	void iniciarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 123.45);
		restTemplate.postForEntity("/cargo/cadastrar", dadosCargo, DadosDetalhamentoCargo.class);
	}

	@BeforeEach
	void iniciar() {
		cargoRepository.deleteAllAndResetSequence();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarCargo() {
		DadosCargo dadosCargo = new DadosCargo("Cargo Teste", 123.45);

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.postForEntity("/cargo/cadastrar", dadosCargo,
				DadosDetalhamentoCargo.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando buscar um cargo por id")
	void listarCargosPorId() {
		iniciarCargo();
		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/buscar/1",
				DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando buscar um cargo por um id inválido")
	void listarCargoPorIdInválido() {

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/buscar/1",
				DadosDetalhamentoCargo.class);
		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar um cargo")
	void listarCargos() {
		iniciarCargo();
		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.getForEntity("/cargo/listar",
				DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve devolver codigo http 200 quando atualizar um cargo")
	void atualizarCargo() {
		iniciarCargo();

		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(1L, "Cargo Atualizar", 543.21);

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarCargoInvalido() {
		DadosAtualizarCargo dadosAtualizarCargo = new DadosAtualizarCargo(1L, "Cargo Atualizar", 543.21);

		ResponseEntity<DadosDetalhamentoCargo> response = restTemplate.exchange("/cargo/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCargo), DadosDetalhamentoCargo.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("Deve retornar um 204 quando deletar")
	void deletaCargo() {
		iniciarCargo();

		ResponseEntity response = restTemplate.exchange("/cargo/deletar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoCargo.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}

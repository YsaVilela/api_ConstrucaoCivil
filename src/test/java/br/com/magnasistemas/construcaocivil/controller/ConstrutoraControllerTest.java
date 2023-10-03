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

import br.com.magnasistemas.construcaocivil.dto.construtora.DadosAtualizarConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ConstrutoraControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;

	@BeforeEach
	void iniciar() {
		equipeRepository.deleteAllAndResetSequence();
		profissionalRepository.deleteAllAndResetSequence();
		construtoraRepository.deleteAllAndResetSequence();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarConstrutora() {

		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.postForEntity("/construtora/cadastrar",
				dadosConstrutora, DadosDetalhamentoConstrutora.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test 
	@DisplayName("deve devolver codigo http 200 quando listar uma construtora por id")
	void listarConstrutora() {

		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);
		
		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.getForEntity("/construtora/buscar/1",
				DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	@DisplayName("deve devolver um erro quando listar uma construtora por un id inexistente")
	void listarConstrutoraPorIdInvalido() {

		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.getForEntity("/construtora/buscar/2",
				DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as construtoras")
	void listarConstrutoras() {

		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);

		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.getForEntity("/construtora/listar/todos",
				DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as construtoras ativas")
	void listarConstrutorasAtivas() {

		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);
		
		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.getForEntity("/construtora/listar",
				DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	@DisplayName("deve devolver codigo http 200 quando atualizar uma Profissional")
	void atualizarEConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);

		DadosAtualizarConstrutora dadosAtualizarConstrutora = new DadosAtualizarConstrutora(1L, "Construtora atualizada", "11987654321",
				"testaAtualiza@gmail.com");
		ResponseEntity<DadosAtualizarConstrutora> response = restTemplate.exchange("/construtora/atualizar",
				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarConstrutora), DadosAtualizarConstrutora.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@DisplayName("deve retornar um erro quando atualizar com um id inválido")
	void atualizarConstrutoraInvalido() {

		DadosAtualizarConstrutora dadosAtualizarConstrutora = new DadosAtualizarConstrutora(1L, "Construtora atualizada", "11987654321",
				"testaAtualiza@gmail.com");
		ResponseEntity<DadosAtualizarConstrutora> response = restTemplate.exchange("/construtora/atualizar",
				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarConstrutora), DadosAtualizarConstrutora.class);

		assertTrue(response.getStatusCode().is5xxServerError());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	@Test
	@DisplayName("deve retornar um 204 quando desativar com um id válido")
	void desativarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);
		
		ResponseEntity response = restTemplate.exchange("/construtora/desativar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoConstrutora.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	@DisplayName("Deve retornar codigo http 200 quando ativar uma Construtora")
	void ativarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);
		
		restTemplate.exchange("/construtora/desativar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoConstrutora.class);
		
		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.exchange("/construtora/ativar/1",
				HttpMethod.PUT, null, DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	
	@Test
	@DisplayName("deve devolver um erro quando ativar uma construtora com um id inválido")
	void ativarConstrutoraIdInvalido() {

		ResponseEntity<DadosDetalhamentoConstrutora> response = restTemplate.exchange("/construtora/ativar/2",
				HttpMethod.PUT, null, DadosDetalhamentoConstrutora.class);

		assertTrue(response.getStatusCode().is5xxServerError());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}
	

	@Test
	@DisplayName("deve retornar um 204 quando deletar com um id válido")
	void deletarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar",dadosConstrutora, DadosDetalhamentoConstrutora.class);

		ResponseEntity response = restTemplate.exchange("/construtora/deletar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoConstrutora.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

	}

}

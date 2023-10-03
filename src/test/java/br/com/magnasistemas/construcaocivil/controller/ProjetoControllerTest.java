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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosAtualizarProjeto;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoProjeto;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosEndereco;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosProjeto;
import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
import br.com.magnasistemas.construcaocivil.entity.dominio.Estados;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;
import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
import br.com.magnasistemas.construcaocivil.repository.ProjetoRepository;
import br.com.magnasistemas.construcaocivil.repository.dominio.CidadesRepository;
import br.com.magnasistemas.construcaocivil.repository.dominio.EstadosRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class ProjetoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EstadosRepository estadosRepository;

	@Autowired
	private CidadesRepository cidadeRepository;

	@Autowired
	private ConstrutoraRepository construtoraRepository;

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	void iniciarConstrutora() {
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
	}

	void iniciarCidade() {
		Estados estado = new Estados();
		estado.setNome("São Paulo");
		estado.setRegiao("Sudeste");
		estado.setUf("Sp");
		estado.setId(1l);

		Cidade cidade = new Cidade();
		cidade.setNome("São Paulo");
		cidade.setEstado(estado);
		cidadeRepository.save(cidade);
	}

	void iniciarProjeto() {
		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", 1L);
		DadosProjeto projeto = new DadosProjeto(1L, "Projeto teste", 12345.67, "DESCRICAO", endereco);
		restTemplate.postForEntity("/projetos/cadastrar", projeto, DadosDetalhamentoProjeto.class);
	}

	@BeforeEach
	void iniciar() {
		iniciarConstrutora();
		iniciarCidade();
	}

	@AfterEach
	void finlizar(){
		enderecoRepository.deleteAllAndResetSequence();
		cidadeRepository.deleteAllAndResetSequence();
		estadosRepository.deleteAll();
		projetoRepository.deleteAllAndResetSequence();
		construtoraRepository.deleteAllAndResetSequence();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarProjeto() {
		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", 1L);

		DadosProjeto projeto = new DadosProjeto(1L, "Projeto teste", 12345.67, "DESCRICAO", endereco);

		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.postForEntity("/projetos/cadastrar", projeto,
				DadosDetalhamentoProjeto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar erro quando criado endereço inválido")
	void criarProjetoComEnderecoInvalido() {
		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", 2L);

		DadosProjeto projeto = new DadosProjeto(1L, "Projeto teste", 12345.67, "DESCRICAO", endereco);

		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.postForEntity("/projetos/cadastrar", projeto,
				DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar um projeto por id")
	void listarProjetoPorId() {
		iniciarProjeto();
		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/buscar/1",
				DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando listar um projeto por um id inválido")
	void listarProjetoPorIdInválido() {
		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/buscar/1",
				DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando listar os projeto")
	void listarProjeto() {
		iniciarProjeto();
		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/listar",
				DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar codigo http 200 quando atualizar um Projeto")
	void atualizarProjeto() {
		iniciarProjeto();

		DadosAtualizarProjeto dadosAtualizarProjeto = new DadosAtualizarProjeto(1l, "Projeto Atualizado", 6543.21,
				"Descricao");

		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.exchange("/projetos/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarProjeto), DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Deve retornar um erro quando atualizar com um id inválido")
	void atualizarProjetoInvalida() {

		DadosAtualizarProjeto dadosAtualizarProjeto = new DadosAtualizarProjeto(1l, "Projeto Atualizado", 6543.21,
				"Descricao");

		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.exchange("/projetos/atualizar", HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarProjeto), DadosDetalhamentoProjeto.class);

		assertTrue(response.getStatusCode().is5xxServerError());
	}

	@Test
	@DisplayName("deve retornar um 204 quando deletar")
	void deletarProjeto() {
		iniciarProjeto();

		ResponseEntity response = restTemplate.exchange("/projetos/deletar/1", HttpMethod.DELETE, null,
				DadosDetalhamentoProjeto.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}

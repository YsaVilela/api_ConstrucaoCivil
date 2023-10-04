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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoEndereco;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoProjeto;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosEndereco;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosProjeto;
import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
import br.com.magnasistemas.construcaocivil.entity.dominio.Estados;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;
import br.com.magnasistemas.construcaocivil.repository.ProjetoRepository;
import br.com.magnasistemas.construcaocivil.repository.dominio.CidadesRepository;
import br.com.magnasistemas.construcaocivil.repository.dominio.EstadosRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class EnderecoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EstadosRepository estadosRepository;

	@Autowired
	private CidadesRepository cidadeRepository;

	@Autowired
	private ProjetoRepository projetoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@BeforeEach
	void iniciar(){
		Estados estado = new Estados();
		estado.setNome("São Paulo");
		estado.setRegiao("Sudeste");
		estado.setUf("Sp");
		estado.setId(1l);

		Cidade cidade = new Cidade();
		cidade.setNome("São Paulo");
		cidade.setEstado(estado);
		cidadeRepository.save(cidade);
		
		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
				"teste@hotmail.com");
		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
		
		DadosEndereco endereco = new DadosEndereco("01234567", "Logradouro", 1L, "A", 1L);
		DadosProjeto projeto = new DadosProjeto(1L, "Projeto teste", 12345.67, "DESCRICAO", endereco);
		restTemplate.postForEntity("/projetos/cadastrar", projeto, DadosDetalhamentoProjeto.class);
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
	@DisplayName("deve devolver codigo http 200 quando listar os endereços")
	void listarEstados() {		
		ResponseEntity<DadosDetalhamentoEndereco> response = restTemplate.getForEntity("/enderecos/listar",
				DadosDetalhamentoEndereco.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	

	@Test
	@DisplayName("deve devolver codigo http 200 quando buscar um endereço por id")
	void listarEnderecoPorId() {

		ResponseEntity<DadosDetalhamentoEndereco> response = restTemplate.getForEntity("/enderecos/buscar/1",
				DadosDetalhamentoEndereco.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("deve devolver codigo http 400 quando buscar um endereço invalido por id")
	void listarEnderecoPorIdInvalido() {

	    ResponseEntity<JsonNode> response = restTemplate.getForEntity("/enderecos/buscar/2", JsonNode.class);

	    assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	
	@Test
	@DisplayName("deve devolver codigo http 200 quando buscar um endereço por id do projeto")
	void listarEnderecoPorIdProjeto() {

		ResponseEntity<DadosDetalhamentoEndereco> response = restTemplate.getForEntity("/enderecos/buscarProjeto/1",
				DadosDetalhamentoEndereco.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	

	@Test
	@DisplayName("deve devolver codigo http 200 quando buscar um endereço por id do projeto")
	void listarEnderecoPorIdProjetoInvalido() {
		
	    ResponseEntity<JsonNode> response = restTemplate.getForEntity("/enderecos/buscarProjeto/2", JsonNode.class);

	    assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	
	
	
	 
	 

}

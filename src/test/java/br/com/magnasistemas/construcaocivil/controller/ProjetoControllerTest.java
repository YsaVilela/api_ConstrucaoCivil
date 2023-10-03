//package br.com.magnasistemas.construcaocivil.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import br.com.magnasistemas.construcaocivil.dto.construtora.DadosConstrutora;
//import br.com.magnasistemas.construcaocivil.dto.construtora.DadosDetalhamentoConstrutora;
//import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoProjeto;
//import br.com.magnasistemas.construcaocivil.dto.projeto.DadosEndereco;
//import br.com.magnasistemas.construcaocivil.dto.projeto.DadosProjeto;
//import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
//import br.com.magnasistemas.construcaocivil.entity.dominio.Estados;
//import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
//import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;
//import br.com.magnasistemas.construcaocivil.repository.EquipeRepository;
//import br.com.magnasistemas.construcaocivil.repository.ProfissionalRepository;
//import br.com.magnasistemas.construcaocivil.repository.ProjetoRepository;
//import br.com.magnasistemas.construcaocivil.repository.dominio.CidadesRepository;
//import br.com.magnasistemas.construcaocivil.repository.dominio.EstadosRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//
//class ProjetoControllerTest {
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Autowired
//	private EstadosRepository estadosRepository;
//	
//	@Autowired
//	private CidadesRepository cidadeRepository;
//	
//	@Autowired
//	private ConstrutoraRepository construtoraRepository;
//	
//	@Autowired
//	private ProjetoRepository projetoRepository;
//	
//	@Autowired
//	private ProfissionalRepository profissionalRepository;
//	
//	@Autowired
//	private EquipeRepository equipeRepository;
//	
//	@Autowired
//	private EnderecoRepository enderecoRepository;
//	
//	void iniciarConstrutora() {
//		DadosConstrutora dadosConstrutora = new DadosConstrutora("12345678901234", "Construtora Teste", "11912345678",
//				"teste@hotmail.com");
//		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
//	}
//
//	@BeforeEach
//	void iniciar() {
//		enderecoRepository.deleteAllAndResetSequence();
//		cidadeRepository.deleteAllAndResetSequence();
//		estadosRepository.deleteAll();
//		projetoRepository.deleteAllAndResetSequence();
//		profissionalRepository.deleteAllAndResetSequence();
//		equipeRepository.deleteAllAndResetSequence();
//		construtoraRepository.deleteAllAndResetSequence();	
//		
//		iniciarConstrutora();
//	}
//	
//	@Test
//	@DisplayName("Deve retornar um created quando criado com sucesso")
//	void criarProjeto() {
//
//		List<Cidade> cidades = cidadeRepository.findAll();
//		System.out.println("cidades: " + cidades);
//		
//		Estados estado = new Estados();
//		estado.setNome("São Paulo");
//		estado.setRegiao("Sudeste");
//		estado.setUf("Sp");
//		estado.setId(1l);
//		estadosRepository.save(estado);
//		
//		System.out.println("Estado foi");
//		
//		Cidade cidade = new Cidade();
//		cidade.setNome("São Paulo");
//		cidade.setEstado(estado);
//		
//		cidadeRepository.save(cidade);
//		
//		System.out.println("cidade tbm"); 
//		
//		DadosEndereco endereco = new DadosEndereco("05728100", "Logradouro", 1L, "B", cidade.getId());
//	 
//		DadosProjeto projeto = new DadosProjeto(1L, "vIZ", 21332.23, "DESCRICAO", endereco);
//		
//		System.out.println("criou tudo");
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.postForEntity("/projetos/cadastrar", projeto,
//				DadosDetalhamentoProjeto.class);
//
//		assertEquals(HttpStatus.CREATED, response.getStatusCode());
//	}

//	@Test
//	@DisplayName("deve devolver codigo http 200 quando listar um projeto por id")
//	void listarProjetoPorId() {
//		DadosConstrutora dadosConstrutora = new DadosConstrutora("12876124253", "Viz", "24977240340",
//				"a0a24=2@gmail.com");
//
//		restTemplate.postForEntity("/construtora/cadastrar", dadosConstrutora, DadosDetalhamentoConstrutora.class);
// 
//
//		DadosEndereco endereco = new DadosEndereco("05728100", "Logradouro", 3L, "B", null);
//	 
//		DadosProjeto projeto = new DadosProjeto(1L, "vIZ", 21332.23, "dESCRICAO", endereco);
//
//		  restTemplate.postForEntity("/projetos/cadastrar", projeto,
//				DadosDetalhamentoProjeto.class);
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/buscar/1",
//				DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is2xxSuccessful());
//
//	}

//	@Test
//	@DisplayName("deve retornar um erro quando listar um projeto por um id inválido")
//	void listarProjetoPorIdInválido() {
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/buscar/14",
//				DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is5xxServerError());
//
//	}
//
//	@Test
//	@DisplayName("deve devolver codigo http 200 quando listar um projeto")
//	void listarProjeto() {
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/listar/todos",
//				DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is2xxSuccessful());
//
//	}
//
//	@Test
//	@DisplayName("deve devolver codigo http 200 quando listar os projetos ativos")
//	void listarProjetosAtivos() {
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.getForEntity("/projetos/listar",
//				DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is2xxSuccessful());
//
//	}
//
//	@Test
//	@DisplayName("deve devolver codigo http 200 quando atualizar um Projeto")
//	void atualizarProjeto() {
//		 DadosEndereco dadosEndereco = new DadosEndereco("02938219","Rua Aleatoria",3l,"a",1l);
//		DadosProjeto dadosProjeto = new DadosProjeto(1L, "Monte", 2332.22, "uma descricao qualquer", dadosEndereco);
//
//		 restTemplate.postForEntity("/projetos/cadastrar",
//				dadosProjeto, DadosDetalhamentoProjeto.class);
//
//
//		 
//
//		DadosAtualizarProjeto dadosAtualizarProjeto = new DadosAtualizarProjeto(1l, "Projeto", 2233.12, "descricao");
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.exchange("/projetos/atualizar",
//				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarProjeto), DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is2xxSuccessful());
//
//	}
//
//	@Test
//	@DisplayName("deve retornar um erro quando atualizar com um id inválido")
//	void atualizarProjetoInvalida() {
//
//		DadosAtualizarProjeto dadosAtualizarProjeto = new DadosAtualizarProjeto(15l, "Projeto", 2233.12, "descricao");
//
//		ResponseEntity<DadosDetalhamentoProjeto> response = restTemplate.exchange("/projetos/atualizar",
//				HttpMethod.PUT, new HttpEntity<>(dadosAtualizarProjeto), DadosDetalhamentoProjeto.class);
//
//		assertTrue(response.getStatusCode().is5xxServerError());
//
//	}
//	
//	@Test
//	@DisplayName("deve retornar um 204 quando deletar com um id válido")
//	void deletarProjeto() {
//		ResponseEntity response = restTemplate.exchange("/projetos/deletar/2", HttpMethod.DELETE, null,
//				DadosDetalhamentoProjeto.class);
//		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//	}

//}

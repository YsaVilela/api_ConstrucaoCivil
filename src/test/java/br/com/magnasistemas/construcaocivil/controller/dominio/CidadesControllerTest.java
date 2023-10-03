package br.com.magnasistemas.construcaocivil.controller.dominio;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.construcaocivil.dto.dominio.DadosCidade;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class CidadesControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("deve devolver codigo http 200 quando listar as cidade")
	void listarCidades() {

		ResponseEntity<DadosCidade> response = restTemplate.getForEntity("/cidades",
				DadosCidade.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	 

}

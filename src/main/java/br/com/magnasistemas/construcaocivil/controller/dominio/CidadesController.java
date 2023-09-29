package br.com.magnasistemas.construcaocivil.controller.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.DTO.dominio.DadosCidade;
import br.com.magnasistemas.construcaocivil.repository.dominio.CidadesRepository;

@RestController
@RequestMapping ("cidades")
public class CidadesController {
	
	@Autowired
	private CidadesRepository repository;
	
	@GetMapping
	public ResponseEntity<Page<DadosCidade>> listar (
			@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
		var ListagemDeCidades = repository.findAll(paginacao).map(DadosCidade::new);
		return ResponseEntity.ok(ListagemDeCidades);
		
	}
 
}
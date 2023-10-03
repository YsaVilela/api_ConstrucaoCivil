package br.com.magnasistemas.construcaocivil.controller.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.dto.dominio.DadosEstados;
import br.com.magnasistemas.construcaocivil.repository.dominio.EstadosRepository;

@RestController
@RequestMapping ("estados")
public class EstadosController {
	
	@Autowired
	private EstadosRepository repository;
	
	@GetMapping
	public ResponseEntity<Page<DadosEstados>> listar (
			@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
		Page<DadosEstados> listagemDeEstados = repository.findAll(paginacao).map(DadosEstados::new);
		return ResponseEntity.ok(listagemDeEstados);
		
	}

} 
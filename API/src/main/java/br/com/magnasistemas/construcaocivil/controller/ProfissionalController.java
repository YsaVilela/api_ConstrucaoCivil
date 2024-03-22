package br.com.magnasistemas.construcaocivil.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.dto.profissional.DadosAtualizarProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.dto.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.service.ProfissionalService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("profissional")
public class ProfissionalController {
	
	@Autowired
	private ProfissionalService service;
	
	
	@PostMapping ("cadastrar")
	@Transactional
	public ResponseEntity<Optional<DadosDetalhamentoProfissional>> cadastrar (@RequestBody @Valid DadosProfissional dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProfissional(dados));
	}  
 
	@GetMapping ("/buscar/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoProfissional>> buscar (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("listar")
	public ResponseEntity<Page<DadosDetalhamentoProfissional>> listar (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("listar/todos")
	public ResponseEntity<Page<DadosDetalhamentoProfissional>> listarTodos (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao)); 
	}
	
	@PutMapping ("atualizar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoProfissional> atualizar(@RequestBody @Valid DadosAtualizarProfissional dados){
		return ResponseEntity.ok(service.atualizar(dados)) ;
	}
	
	@PutMapping ("ativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoProfissional> ativar(@PathVariable Long id){
		return ResponseEntity.ok(service.ativar(id));
	}
	 
	@DeleteMapping ("desativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoProfissional> desativar(@PathVariable Long id){
		return ResponseEntity.ok(service.desativar(id));
	}
	
	@DeleteMapping ("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


}

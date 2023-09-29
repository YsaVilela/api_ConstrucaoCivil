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

import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosAtualizarConstrutora;
import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosConstrutora;
import br.com.magnasistemas.construcaocivil.DTO.construtora.DadosDetalhamentoConstrutora;
import br.com.magnasistemas.construcaocivil.entity.Construtora;
import br.com.magnasistemas.construcaocivil.service.ConstrutoraService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("construtora")
public class ConstrutoraController {
	
	@Autowired
	private ConstrutoraService service;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Construtora> cadastrar (@RequestBody @Valid DadosConstrutora dados) {
		service.criarConstrutora(dados);	
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
 
	@GetMapping ("/buscar/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoConstrutora>> buscar (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("listar")
	public ResponseEntity<Page<DadosDetalhamentoConstrutora>> listar (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("listar/todos")
	public ResponseEntity<Page<DadosDetalhamentoConstrutora>> listarTodos (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao)); 
	}
	
	@PutMapping ("atualizar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoConstrutora> atualizar(@RequestBody @Valid DadosAtualizarConstrutora dados){
		return ResponseEntity.ok(service.atualizar(dados)) ;
	}
	 
	@DeleteMapping ("desativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoConstrutora> desativar(@PathVariable Long id){
		return ResponseEntity.ok(service.desativar(id));
	}
	
	@DeleteMapping ("deletar/{id}")
	@Transactional
	public ResponseEntity<Construtora> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}

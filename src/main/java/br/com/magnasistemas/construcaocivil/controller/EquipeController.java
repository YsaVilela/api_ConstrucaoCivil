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

import br.com.magnasistemas.construcaocivil.dto.equipe.DadosAtualizarEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.dto.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.service.EquipeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("equipe")
public class EquipeController { 
	
	@Autowired
	private EquipeService service;
	
	
	@PostMapping ("/cadastrar")
	@Transactional
	public ResponseEntity<Optional<DadosDetalhamentoEquipe>> cadastrar (@RequestBody @Valid DadosEquipe dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEquipe(dados));
	}
 
	@GetMapping ("/buscar/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoEquipe>> buscar (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("/listar")
	public ResponseEntity<Page<DadosDetalhamentoEquipe>> listar (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("/listar/todos")
	public ResponseEntity<Page<DadosDetalhamentoEquipe>> listarTodos (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao)); 
	}
	
	@PutMapping ("/atualizar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoEquipe> atualizar(@RequestBody @Valid DadosAtualizarEquipe dados){
		return ResponseEntity.ok(service.atualizar(dados));
	}
	 
	@PutMapping ("/ativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoEquipe> ativar(@PathVariable Long id){
		return ResponseEntity.ok(service.ativar(id)) ;
	}
	
	@DeleteMapping ("/desativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoEquipe> desativar(@PathVariable Long id){
		return ResponseEntity.ok(service.desativar(id));
	}
	
	@DeleteMapping ("/deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id){
		service.deletar(id); 
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


}




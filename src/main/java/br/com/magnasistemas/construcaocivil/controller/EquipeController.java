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

import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosAtualizarEquipe;
import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosDetalhamentoEquipe;
import br.com.magnasistemas.construcaocivil.DTO.equipe.DadosEquipe;
import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosAtualizarProfissional;
import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosDetalhamentoProfissional;
import br.com.magnasistemas.construcaocivil.DTO.profissional.DadosProfissional;
import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.entity.Profissional;
import br.com.magnasistemas.construcaocivil.service.EquipeService;
import br.com.magnasistemas.construcaocivil.service.ProfissionalService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("equipe")
public class EquipeController { 
	
	@Autowired
	private EquipeService service;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Profissional> cadastrar (@RequestBody @Valid DadosEquipe dados) {
		service.criarEquipe(dados);	
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
 
	@GetMapping ("/buscar/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoEquipe>> buscar (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("listar")
	public ResponseEntity<Page<DadosDetalhamentoEquipe>> listar (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("listar/todos")
	public ResponseEntity<Page<DadosDetalhamentoEquipe>> listarTodos (
			@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao)); 
	}
	
	@PutMapping ("atualizar")
	@Transactional
	public ResponseEntity<DadosDetalhamentoEquipe> atualizar(@RequestBody @Valid DadosAtualizarEquipe dados){
		return ResponseEntity.ok(service.atualizar(dados));
	}
	 
	@DeleteMapping ("desativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoEquipe> desativar(@PathVariable Long id){
		return ResponseEntity.ok(service.desativar(id));
	}
	
	@DeleteMapping ("deletar/{id}")
	@Transactional
	public ResponseEntity<Equipe> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}


}




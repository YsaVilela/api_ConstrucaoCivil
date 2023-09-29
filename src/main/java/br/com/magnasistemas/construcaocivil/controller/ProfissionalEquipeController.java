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


import br.com.magnasistemas.construcaocivil.DTO.profissionalEquipe.DadosDetalhamentoProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.DTO.profissionalEquipe.DadosProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.entity.Projeto;
import br.com.magnasistemas.construcaocivil.service.ProfissionalEquipeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("profissional/equipe")
public class ProfissionalEquipeController {

	@Autowired 
	private ProfissionalEquipeService service;
	
		@PostMapping ("cadastrar")
		@Transactional
		public ResponseEntity<Projeto> cadastrar (@RequestBody @Valid DadosProfissionalEquipe dados) {
			service.criarProfissioanlEquipe(dados);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		@GetMapping ("/buscarEquipe/{id}")
		public ResponseEntity<Page<DadosDetalhamentoProfissionalEquipe>> buscarEquipe (
				@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao,
				@PathVariable Long id) {
			return ResponseEntity.ok(service.listarEquipe(paginacao,id));
		}
		
		@GetMapping ("/buscarProfissional/{id}")
		public ResponseEntity<Page<DadosDetalhamentoProfissionalEquipe>> buscarProfissional (
				@PathVariable Long id, @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
			return ResponseEntity.ok(service.listarProfissional(paginacao, id));
		}
		 
//		@GetMapping ("listar")
//		public ResponseEntity<Page<DadosDetalhamentoCargo>> listar (
//				@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
//			return ResponseEntity.ok(service.listar(paginacao));
//		}
//		
//		@PutMapping ("atualizar")
//		@Transactional
//		public ResponseEntity<DadosDetalhamentoCargo> atualizar(@RequestBody @Valid DadosAtualizarCargo dados){
//			return ResponseEntity.ok(service.atualizar(dados)) ;
//		}
//		
//		@DeleteMapping ("deletar/{id}")
//		@Transactional
//		public ResponseEntity<Cargo> deletar(@PathVariable Long id){
//			service.deletar(id);
//			return ResponseEntity.noContent().build();
//		}

	
	

}

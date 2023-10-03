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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosDetalhamentoProfissionalEquipe;
import br.com.magnasistemas.construcaocivil.dto.profissional_equipe.DadosProfissionalEquipe;
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
		public ResponseEntity<Optional<DadosDetalhamentoProfissionalEquipe>> cadastrar (@RequestBody @Valid DadosProfissionalEquipe dados) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProfissioanlEquipe(dados));
		}  
		
		@GetMapping ("/buscarEquipe/{id}")
		public ResponseEntity<Page<DadosDetalhamentoProfissionalEquipe>> buscarEquipe (
				@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao,
				@PathVariable Long id) {
			return ResponseEntity.ok(service.listarEquipe(id,paginacao));
		}
		
		@GetMapping ("/buscarProfissional/{id}")
		public ResponseEntity<Page<DadosDetalhamentoProfissionalEquipe>> buscarProfissional (
				@PathVariable Long id, @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
			return ResponseEntity.ok(service.listarProfissional(id,paginacao));
		}
		 
		@GetMapping ("listar")
		public ResponseEntity<Page<DadosDetalhamentoProfissionalEquipe>> listar (
				@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
			return ResponseEntity.ok(service.listar(paginacao));
		}
		
	 
		@DeleteMapping ("deletar/{id}")
		@Transactional
		public ResponseEntity<HttpStatus> deletar(@PathVariable Long id){
			service.deletar(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

	
	

}

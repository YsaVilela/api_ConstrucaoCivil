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

import br.com.magnasistemas.construcaocivil.dto.cargo.DadosAtualizarCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosCargo;
import br.com.magnasistemas.construcaocivil.dto.cargo.DadosDetalhamentoCargo;
import br.com.magnasistemas.construcaocivil.service.CargoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("cargo")
public class CargoController{
	
	@Autowired 
	private CargoService service; 
	
		@PostMapping ("cadastrar")
		@Transactional
		public ResponseEntity<Optional<DadosDetalhamentoCargo>> cadastrar (@RequestBody @Valid DadosCargo dados) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCargo(dados));
		}
		
		@GetMapping ("/buscar/{id}")
		public ResponseEntity<Optional<DadosDetalhamentoCargo>> buscar (@PathVariable Long id) {
			return ResponseEntity.ok(service.buscarPorId(id));
		}
		 
		@GetMapping ("listar")
		public ResponseEntity<Page<DadosDetalhamentoCargo>> listar (
				@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
			return ResponseEntity.ok(service.listar(paginacao));
		}
		
		@PutMapping ("atualizar")
		@Transactional
		public ResponseEntity<DadosDetalhamentoCargo> atualizar(@RequestBody @Valid DadosAtualizarCargo dados){
			return ResponseEntity.ok(service.atualizar(dados)) ;
		}
		
		@DeleteMapping ("deletar/{id}")
		@Transactional
		public ResponseEntity<HttpStatus> deletar(@PathVariable Long id){
			service.deletar(id); 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

}

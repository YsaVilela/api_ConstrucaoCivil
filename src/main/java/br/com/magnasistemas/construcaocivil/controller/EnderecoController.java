package br.com.magnasistemas.construcaocivil.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoEndereco;
import br.com.magnasistemas.construcaocivil.service.EnderecoService;

@RestController
@RequestMapping ("enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoService service;
	
	@GetMapping ("/listar")
	public ResponseEntity<Page<DadosDetalhamentoEndereco>> listar (
			@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
		return ResponseEntity.ok(service.listar(paginacao));
	}
	
	@GetMapping ("/buscar/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoEndereco>> buscarPorId (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping ("/buscarProjeto/{id}")
	public ResponseEntity<Optional<DadosDetalhamentoEndereco>> buscarPorIdProjeto (@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorIdProjeto(id));
	}
	

}

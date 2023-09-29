package br.com.magnasistemas.construcaocivil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.construcaocivil.DTO.projeto.DadosDetalhamentoEndereco;
import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;

@RestController
@RequestMapping ("enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository repository;
	
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoEndereco>> listar (
			@PageableDefault(size = 1, sort = {"id"}) Pageable paginacao) {
		var ListagemDeEstados = repository.findAll(paginacao).map(DadosDetalhamentoEndereco::new);
		return ResponseEntity.ok(ListagemDeEstados);
	}

}

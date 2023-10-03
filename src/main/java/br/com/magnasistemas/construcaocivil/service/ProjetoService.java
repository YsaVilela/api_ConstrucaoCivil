package br.com.magnasistemas.construcaocivil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.construcaocivil.dto.projeto.DadosAtualizarProjeto;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosDetalhamentoProjeto;
import br.com.magnasistemas.construcaocivil.dto.projeto.DadosProjeto;
import br.com.magnasistemas.construcaocivil.entity.Endereco;
import br.com.magnasistemas.construcaocivil.entity.Projeto;
import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
import br.com.magnasistemas.construcaocivil.exception.BuscarException;
import br.com.magnasistemas.construcaocivil.repository.ConstrutoraRepository;
import br.com.magnasistemas.construcaocivil.repository.ProjetoRepository;
import br.com.magnasistemas.construcaocivil.repository.EnderecoRepository;
import br.com.magnasistemas.construcaocivil.repository.dominio.CidadesRepository;
import br.com.magnasistemas.construcaocivil.service.validacoes.construtora.ValidadorConstrutora;
import jakarta.validation.Valid;

@Service
public class ProjetoService {
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadesRepository cidadeRepository;
	
	@Autowired
	private List<ValidadorConstrutora> validadoresConstrutora;
	
	
	public Optional<DadosDetalhamentoProjeto> criarProjeto (@Valid DadosProjeto dados) {
		
		validadoresConstrutora.forEach(v -> v.validar(dados.idConstrutora()));

		Projeto projeto = new Projeto();
		projeto.setNome(dados.nome());
		projeto.setOrcamentoMaximo(dados.orcamentoMaximo());
		projeto.setDescricao(dados.descricao());
		projeto.setConstrutora(construtoraRepository.getReferenceById(dados.idConstrutora()));
		
		projetoRepository.save(projeto);
		
		Endereco endereco = new Endereco();
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		
		Optional<Cidade> validarCidade = cidadeRepository.findById(dados.endereco().idCidade());
		if (validarCidade.isEmpty()) 
			throw new BuscarException ("Cidade não encontrada");
		endereco.setCidade(cidadeRepository.getReferenceById(dados.endereco().idCidade()));	
		
		endereco.setProjeto(projetoRepository.getReferenceById(projeto.getId()));
		
		enderecoRepository.save(endereco);
		 
		return projetoRepository.findById(projeto.getId()).map(DadosDetalhamentoProjeto::new); 
	} 

	public Optional<DadosDetalhamentoProjeto> buscarPorId(Long id) {
		Optional<Projeto> validarProjeto = projetoRepository.findById(id);
		if (validarProjeto.isEmpty()) 
			throw new BuscarException ("Projeto não encontrado");
		
        return projetoRepository.findById(id).map(DadosDetalhamentoProjeto::new);
	}
	
	public Page<DadosDetalhamentoProjeto> listar(Pageable paginacao) {
        return projetoRepository.findAll(paginacao).map(DadosDetalhamentoProjeto::new);
	} 
	 
	public DadosDetalhamentoProjeto atualizar(@Valid DadosAtualizarProjeto dados) {
		Optional<Projeto> validarProjeto = projetoRepository.findById(dados.id());
		if (validarProjeto.isEmpty()) 
			throw new BuscarException ("Projeto não encontrado");
		
		Projeto projeto = projetoRepository.getReferenceById(dados.id());
				projeto.setNome(dados.nome());
				projeto.setOrcamentoMaximo(dados.orcamentoMaximo());
				projeto.setDescricao(dados.descricao());
		projetoRepository.save(projeto);
		return new DadosDetalhamentoProjeto(projeto);
	}
	
	public void deletar(Long id) {
		projetoRepository.deleteById(id);
	}

}

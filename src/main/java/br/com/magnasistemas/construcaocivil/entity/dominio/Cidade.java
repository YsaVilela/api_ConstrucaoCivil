package br.com.magnasistemas.construcaocivil.entity.dominio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.construcaocivil.entity.Endereco;
import br.com.magnasistemas.construcaocivil.entity.Projeto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "tb_cidade")
@Entity (name = "Cidade")
public class Cidade {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_estado")
	private Estados fk_estado;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade")
    @JsonIgnore
    private List<Endereco> construtora = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public Estados getEstado() {
		return fk_estado;
	}

	public String getNome() {
		return nome;
	}
	
	
}

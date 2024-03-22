package br.com.magnasistemas.construcaocivil.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "tb_cargo")
@Entity (name = "Cargo")
public class Cargo {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome", unique = true) 
	private String nome;
	
	@Column(name = "remuneracao") 
	private Double remuneracao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    @JsonIgnore
    private List<Profissional> profissionais = new ArrayList<>();

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(Double remuneracao) {
		this.remuneracao = remuneracao;
	}

	public Long getId() {
		return id;
	}

	
}

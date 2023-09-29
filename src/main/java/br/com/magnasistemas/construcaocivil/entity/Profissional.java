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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "tb_profissional")
@Entity (name = "Profissional")
public class Profissional {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	private String cpf;
	private String nome;
	private String telefone;
	private boolean status;
	
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_construtora")
    private Construtora construtora;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cargo")
    private Cargo cargo;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profissional")
    @JsonIgnore
    private List<ProfissionalEquipe> profissional = new ArrayList<>();

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Construtora getConstrutora() {
		return construtora;
	}

	public void setConstrutora(Construtora construtora) {
		this.construtora = construtora;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Long getId() {
		return id;
	}

}

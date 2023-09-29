package br.com.magnasistemas.construcaocivil.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.construcaocivil.enumerator.Turno;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "tb_equipe")
@Entity (name = "Equipe")
public class Equipe {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Turno turno;
	private boolean status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_construtora")
    private Construtora construtora;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "equipe")
    @JsonIgnore
    private List<ProfissionalEquipe> equipe = new ArrayList<>();

    
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
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

	public Long getId() {
		return id;
	}
    
   
}

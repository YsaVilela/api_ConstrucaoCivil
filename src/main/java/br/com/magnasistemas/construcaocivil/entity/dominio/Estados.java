package br.com.magnasistemas.construcaocivil.entity.dominio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "tb_estado")
@Entity (name = "Estados")

public class Estados {
	@Id 
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "regiao")
	private String regiao;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_estado")
	protected List<Estados> estado = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getUf() {
		return uf;
	}
	public String getRegiao() {
		return regiao;
	}	
}

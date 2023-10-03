package br.com.magnasistemas.construcaocivil.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "tb_projeto")
@Entity (name = "Projeto")
public class Projeto {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	 
	@Column(name = "orcamento_maximo") 
	private Double orcamentoMaximo;

	@Column(name = "descricao") 
	private String descricao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_construtora")
    private Construtora construtora;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getOrcamentoMaximo() {
		return orcamentoMaximo;
	}

	public void setOrcamentoMaximo(Double orcamentoMaximo) {
		this.orcamentoMaximo = orcamentoMaximo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public Construtora getConstrutora() {
		return construtora;
	}

	public void setConstrutora(Construtora construtora) {
		this.construtora = construtora;
	}

	
}

package br.com.magnasistemas.construcaocivil.entity;


import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "tb_endereco")
@Entity (name = "Endereco")
public class Endereco {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "cep") 
	private String cep;
	
	@Column(name = "logradouro") 
	private String logradouro;
	
	@Column(name = "numero") 
	private Long numero;
	
	@Column(name = "complemento") 
	private String complemento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cidade")
    private Cidade cidade;
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "fk_projeto", unique = true)
	private Projeto projeto;

	
	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}

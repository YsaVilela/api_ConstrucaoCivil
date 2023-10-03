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

	@Table(name = "tb_construtora")
	@Entity(name = "Construtora")
	public class Construtora {
		@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(name = "cnpj", unique = true)
		private String cnpj;
		
		@Column(name = "nome", nullable = false)
		private String nome;
		
		@Column(name = "telefone", unique = true)
		private String telefone;
		
		@Column(name = "email", unique = true)
		private String email;
		
		@Column(name = "status")
		private boolean status;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "construtora")
	    @JsonIgnore
	    private List<Projeto> projetos = new ArrayList<>();
		
		
		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public String getCnpj() {
			return cnpj;
		}


		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
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


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public boolean isStatus() {
			return status;
		}


		public void setStatus(boolean status) {
			this.status = status;
		}
		
		
}
 
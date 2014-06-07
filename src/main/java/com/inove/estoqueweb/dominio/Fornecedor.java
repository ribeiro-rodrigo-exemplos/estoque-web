package com.inove.estoqueweb.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Fornecedor {

	@Column(name="razaoSocial",nullable=false,unique=true)
	private String razaoSocial; 
	@Column(name="email",nullable=true)
	private String email; 
	@Column(name="cnpj",nullable=true,unique=true)
	private String cnpj; 
	@Column(name="cpf",nullable=true)
	private String cpf; 
	@Column(name="endereco")
	private String endereco; 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	@OneToMany(mappedBy="id",fetch=FetchType.LAZY)
	private List<Produto> produtos; 
	
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public Long getId() {
		return id;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	private String telefone; 
}

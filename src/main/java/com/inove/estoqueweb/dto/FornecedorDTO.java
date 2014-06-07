package com.inove.estoqueweb.dto;

public class FornecedorDTO {

	private Integer id; 
	private String razaoSocial; 
	private String email; 
	private String cnpj; 
	private String cpf; 
	private String endereco;
	
	public FornecedorDTO(Integer id){
		
		this.id = id; 
	}
	
	public Integer getId() {
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
}

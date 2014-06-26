package com.inove.estoqueweb.dto;

public class FornecedorDTO {

	private Long id; 
	private String razaoSocial; 
	private String email; 
	private String cnpj; 
	private String cpf; 
	private String endereco;
	private String telefone;
	
	public FornecedorDTO(){
		
	}
	
	public FornecedorDTO(Long id){
		
		this.id = id; 
	}
	
	public void setId(Long id){
		
		this.id = id; 
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
	
	public void setTelefone(String telefone){
		
		this.telefone = telefone; 
		
	}

	public String getTelefone() {
		// TODO Auto-generated method stub
		return telefone;
	} 
}

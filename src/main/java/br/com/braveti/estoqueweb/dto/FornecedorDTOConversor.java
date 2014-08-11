package br.com.braveti.estoqueweb.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.braveti.estoqueweb.dominio.Fornecedor;

@Component
public class FornecedorDTOConversor implements DTOConversor<FornecedorDTO,Fornecedor> {

	public FornecedorDTO converterDominio(Fornecedor fornecedor){
		
		FornecedorDTO dto = new FornecedorDTO(fornecedor.getId()); 
		
		dto.setCnpj(fornecedor.getCnpj());
		dto.setCpf(fornecedor.getCpf());
		dto.setEmail(fornecedor.getEmail());
		dto.setEndereco(fornecedor.getEndereco());
		dto.setRazaoSocial(fornecedor.getRazaoSocial());
		
		return dto; 
	}
	public Fornecedor converterTransferencia(FornecedorDTO dto){
		
		Fornecedor fornecedor = new Fornecedor(); 
		fornecedor.setId(dto.getId()); 
		fornecedor.setCnpj(dto.getCnpj());
		fornecedor.setCpf(dto.getCpf());
		fornecedor.setEmail(dto.getEmail());
		fornecedor.setEndereco(dto.getEndereco());
		fornecedor.setRazaoSocial(dto.getRazaoSocial());
		fornecedor.setTelefone(dto.getTelefone());
		
		return fornecedor; 
	}
	public List<FornecedorDTO> converterLista(List<Fornecedor> lista){
		
		List<FornecedorDTO> dtoLista = new ArrayList<FornecedorDTO>();  
		
		for(Fornecedor fornecedor:lista){
			
			FornecedorDTO dto = converterDominio(fornecedor); 
			dtoLista.add(dto);
		}
		
		return dtoLista; 
	}
}

package br.com.braveti.estoqueweb.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.braveti.estoqueweb.dominio.Estoque;

@Component
public class EstoqueDTOConversor implements DTOConversor<EstoqueDTO,Estoque> {

	public EstoqueDTO converterDominio(Estoque estoque){
		
		EstoqueDTO dto = new EstoqueDTO(estoque.getId());
		
		dto.setDescricao(estoque.getDescricao());
		dto.setNome(estoque.getNome());
		
		return dto; 
	}
	
	public Estoque converterTransferencia(EstoqueDTO dto){
		
		Estoque estoque = new Estoque(); 
		
		estoque.setDescricao(dto.getDescricao());
		estoque.setNome(dto.getNome());
		estoque.setId(dto.getId()); 
		
		return estoque; 
	}
	
	public List<EstoqueDTO> converterLista(List<Estoque> lista){
		
		List<EstoqueDTO> dtoLista = new ArrayList<EstoqueDTO>(); 
		
		for(Estoque estoque:lista){
			
			EstoqueDTO dto = converterDominio(estoque);
			dtoLista.add(dto); 
		}
		
		return dtoLista; 
	}
}

package com.inove.estoqueweb.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dominio.Categoria;

@Component
public class CategoriaDTOConversor implements DTOConversor<CategoriaDTO,Categoria> {

	
	public CategoriaDTO converterDominio(Categoria categoria){
		
		CategoriaDTO dto = new CategoriaDTO(categoria.getId());
		
		dto.setDescricao(categoria.getDescricao());
		dto.setNome(categoria.getNome());
		
		return dto; 
	}
	
	public Categoria converterTransferencia(CategoriaDTO dto){
		
		Categoria categoria = new Categoria(); 
		
		categoria.setDescricao(dto.getDescricao());
		categoria.setNome(dto.getNome());
		categoria.setId(dto.getId()); 

		return categoria; 
	}
	
	public List<CategoriaDTO> converterLista(List<Categoria> categorias){
		
		List<CategoriaDTO> dtoLista = new ArrayList<CategoriaDTO>(); 
		
		for(Categoria categoria:categorias){
			
			CategoriaDTO dto = converterDominio(categoria); 
			
			dtoLista.add(dto); 
		}
		
		return dtoLista; 
	}
}

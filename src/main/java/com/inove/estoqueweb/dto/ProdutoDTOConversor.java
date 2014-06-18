package com.inove.estoqueweb.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dominio.Produto;

@Component
public class ProdutoDTOConversor implements DTOConversor<ProdutoDTO,Produto> {

public ProdutoDTO converterDominio(Produto produto){
		
		return null; 
	}
	public Produto converterTransferencia(ProdutoDTO dto){
		
		return null; 
	}
	public List<ProdutoDTO> converterLista(List<Produto> lista){
		
		return null; 
	}
}

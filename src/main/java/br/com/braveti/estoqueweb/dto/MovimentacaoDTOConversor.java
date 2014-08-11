package com.inove.estoqueweb.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dominio.Movimentacao;

@Component
public class MovimentacaoDTOConversor implements DTOConversor<MovimentacaoDTO,Movimentacao> {

public MovimentacaoDTO converterDominio(Movimentacao movimentacao){
	
	MovimentacaoDTO dto = new MovimentacaoDTO(movimentacao.getId()); 
	dto.setDataHora(movimentacao.getDataHora());
	dto.setProdutoId(movimentacao.getProduto().getId());
	dto.setQuantidade(movimentacao.getQuantidade());
	dto.setTipo(movimentacao.getTipo());
	dto.setEstoqueId(movimentacao.getEstoque().getId());
	
		return dto; 
	}
	// metodo nao utilizado para esse dominio
	public Movimentacao converterTransferencia(MovimentacaoDTO dto){
		
		return null; 
		
	}
	public List<MovimentacaoDTO> converterLista(List<Movimentacao> lista){
		
		List<MovimentacaoDTO> dtoLista = new ArrayList<MovimentacaoDTO>(); 
		
		for(Movimentacao movimentacao:lista){
			
			MovimentacaoDTO dto = converterDominio(movimentacao); 
			dtoLista.add(dto); 
		}
		
		return dtoLista; 
	}
}

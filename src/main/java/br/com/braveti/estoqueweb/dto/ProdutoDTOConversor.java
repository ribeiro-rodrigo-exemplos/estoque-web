package com.inove.estoqueweb.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Produto;

@Component
public class ProdutoDTOConversor implements DTOConversor<ProdutoDTO,Produto> {

	public ProdutoDTO converterDominio(Produto produto){
	
		ProdutoDTO dto = new ProdutoDTO(produto.getId()); 
		
		if(produto.getCategoria()!=null)
			dto.setCategoriaId(produto.getCategoria().getId());
		if(produto.getFornecedor()!=null)
			dto.setFornecedorId(produto.getFornecedor().getId()); 
		if(produto.getEstoqueAtual()!=null)
			dto.setEstoqueAtualId(produto.getEstoqueAtual().getId());
		
		dto.setDescricao(produto.getDescricao());
		dto.setNome(produto.getNome());
		dto.setQuantidadeAtual(produto.getQuantidadeAtual());
		dto.setQuantidadeMinima(produto.getQuantidadeMinima());
		
	
		return dto; 
	}
	public Produto converterTransferencia(ProdutoDTO dto){
		
		Produto produto = new Produto(); 
		
		produto.setId(dto.getId());
		produto.setCategoria(new Categoria(dto.getCategoriaId()));
		produto.setEstoqueAtual(new Estoque(dto.getEstoqueAtualId())); 
		produto.setFornecedor(new Fornecedor(dto.getFornecedorId())); 
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setQuantidadeAtual(dto.getQuantidadeAtual());
		produto.setQuantidadeMinima(dto.getQuantidadeMinima());
		
		return produto; 
	}
	public List<ProdutoDTO> converterLista(List<Produto> lista){
		
		List<ProdutoDTO> dtoLista = new ArrayList<ProdutoDTO>(); 
		
		for(Produto produto:lista){
			
			ProdutoDTO dto = converterDominio(produto); 
			dtoLista.add(dto); 
			
		}
		
		return dtoLista; 
	}
}

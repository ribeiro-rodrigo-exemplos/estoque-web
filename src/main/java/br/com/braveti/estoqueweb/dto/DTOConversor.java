package br.com.braveti.estoqueweb.dto;

import java.util.List;

public interface DTOConversor<T,D> {

	public T converterDominio(D dominio); 
	public D converterTransferencia(T transferencia); 
	public List<T> converterLista(List<D> dominios); 
}

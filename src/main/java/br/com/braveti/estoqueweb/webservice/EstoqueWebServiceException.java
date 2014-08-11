package br.com.braveti.estoqueweb.webservice;

import javax.xml.ws.WebFault;

@WebFault(name="WebServiceException")
public class EstoqueWebServiceException extends Exception {

	private EstoqueWebServiceFault fault; 
	
	public EstoqueWebServiceException(String message, EstoqueWebServiceFault fault){
		
		this(message,fault,null);
	}
	
	public EstoqueWebServiceException(String message, EstoqueWebServiceFault fault, Throwable throwable){
		
		super(message,throwable); 
		
		this.fault = fault; 
		
	}
	
	public EstoqueWebServiceFault getFaultInfo(){
		
		return fault; 
	}
}

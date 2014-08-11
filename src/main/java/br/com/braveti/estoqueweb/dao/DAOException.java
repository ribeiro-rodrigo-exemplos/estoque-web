package com.inove.estoqueweb.dao;

public class DAOException extends Exception {

	public DAOException(){
		
		this(null);
	}
	
	public DAOException(String message){
		
		this(message,null); 
	}
	
	public DAOException(String message, Throwable cause){
		
		super(message,cause); 
	}
}

package com.germanrobledo.callcenter;

/**
 * La clase CallHandler modela al operador de llamada, el cual tiene dos atributos
 * el nombre del operador de llamada, y el tipo de operador de llamada (Operador, Supervisor o Director) 
 */
public class CallHandler {
	private String name;
	private CallHandlerEnum type;
	
	public CallHandler(CallHandlerEnum type, String name){
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CallHandlerEnum getType() {
		return type;
	}

	public void setType(CallHandlerEnum type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.getName() + "(" + this.getType().toString() + ")";
	}
	
}

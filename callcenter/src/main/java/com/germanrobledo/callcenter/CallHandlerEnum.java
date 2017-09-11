package com.germanrobledo.callcenter;

/**
 * Este enum clasifica los tipos de operadores de llamada (CallHandler) en Operador, Supervisor o Director
 */
public enum CallHandlerEnum {
	Operator("Operator"),
	Supervisor("Supervisor"),
	Director("Director");
	
	private final String text;
	
	private CallHandlerEnum(final String text) {
        this.text = text;
    }
	
	 @Override
	    public String toString() {
	        return text;
	    }
}

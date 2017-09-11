package com.germanrobledo.callcenter;

import java.util.concurrent.TimeUnit;

/**
 * La clase Call modela la llamada recibe por parametros en el constructor la duracion de la llamada en segundos
 * y la cola de operadores de llamada.
 * En el comiezo toma un operador de llamada de la cola (esta bloquea la ejecucion si esta vacia, hasta que
 * uno este disponible. Al finalizar la llamada devuelve al operador a la cola. 
 *
 */
public class Call implements Runnable {

	private int lenCallInSec;
	
	private CallHandler callHandler;
	
	private CallHandlerQueue callHandlerQueue;
	
	public Call(int sec, CallHandlerQueue callHandlerQueue){
		this.lenCallInSec = sec;
		this.callHandlerQueue = callHandlerQueue;
	}
	
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		try {
			this.callHandler = callHandlerQueue.take();
		} catch (InterruptedException e1) {
			System.err.println("ERROR: el thread " + threadName + " No pudo obtener un operador, no se puedo procesar una llamada de " + this.lenCallInSec + " segundos.");
		}
		System.out.println("comienza ejecucion llamada (" + threadName + ") duracion " + this.lenCallInSec + " segundos. Atendida por " + callHandler.toString());
		try {
			TimeUnit.SECONDS.sleep(lenCallInSec);
		} catch (InterruptedException e) {
			System.err.println("ERROR: la llamada del thread " + threadName + " atendida por " + callHandler.toString() + " fue interrumpida.");
		}
		finally {
			System.out.println("finaliza llamada (" + threadName + ") atendida por " + callHandler.getName() + "(" + callHandler.getType().toString() + ").");
			try {
				this.callHandlerQueue.put(callHandler);
			} catch (InterruptedException e) {
				System.err.println("ERROR: el thread " + threadName + " No pudo liberar al operador.");
			}
		}
		
	}

}

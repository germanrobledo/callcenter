package com.germanrobledo.callcenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * La clase Dispatcher ejecuta las llamadas en distintos threads
 * Para eso utiliza un ExecutorServices que administra los threads
 * hasta un maximo de MAX_THREADS si la cantidad de llamadas supera este numero
 * las llamadas son encoladas en espera hasta que se liberen Threads
 * El ExecutorService es inicializado en el constructor y se detiene con el metodo stop()
 * Cuando se ejecuta este metodo se espera un tiempo maximo de SECONDS_TO_SHUTDOWN segundos que los
 * threads que se estan ejecutando terminen. Una vez superado este tiempo los threads que aun esten
 * en ejecucion son interrumpidos. 
 */
public class Dispatcher {
	
	private static final int MAX_THREADS = 10;
	private static final int SECONDS_TO_SHUTDOWN = 5;
	private ExecutorService executor;
	private CallHandlerQueue handlersQueue;

	public Dispatcher(CallHandlerQueue handlerQueue){
		this.handlersQueue = handlerQueue;
		this.executor = Executors.newFixedThreadPool(MAX_THREADS);
	}
	
	public void dispatchCall(int seconds){
		Call call = new Call(seconds, this.handlersQueue);
		executor.submit(call);
	}
	
	public void stop(){
		try {
		    System.out.println("Comienza el apagado del dispatcher, tiempo maximo de espera " + SECONDS_TO_SHUTDOWN + " segundos");
		    executor.shutdown();
		    executor.awaitTermination(SECONDS_TO_SHUTDOWN, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("Tareas Interrumpidas.");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("Se cancelaron las tareas no finalizadas.");
		    }
		    executor.shutdownNow();
		    System.out.println("Apagado del dispatcher finalizada.");
		}
	}
}

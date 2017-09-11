package com.germanrobledo.callcenter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.germanrobledo.callcenter.CallHandler;
import com.germanrobledo.callcenter.CallHandlerEnum;
import com.germanrobledo.callcenter.CallHandlerQueue;
import com.germanrobledo.callcenter.Dispatcher;

/**
 * La clase Callcenter implementa el metodo main principal de la aplicacion, la cual
 * Ejecuta un ejemplo 3 operadores , 2 supervisores y 1 director
 * Y simula varias llamadas. 
 */
public class Callcenter 
{
    public static void main( String[] args ) throws InterruptedException
    {
//    	general la cola de operadores de llamada con 3 operadores 2 supervisores y 1 director
    	CallHandlerQueue queue = new CallHandlerQueue();
		queue.put(new CallHandler(CallHandlerEnum.Director, "Pedro"));
		queue.put(new CallHandler(CallHandlerEnum.Supervisor, "Martin"));
		queue.put(new CallHandler(CallHandlerEnum.Supervisor, "Lucas"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Pablo"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Miguel"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Fernando"));
			
		
		Dispatcher dispatcher = new Dispatcher(queue);
		Random rn = new Random();
//		genera 3 proceso de generacion de llamadas al azar
		for(int i = 0; i < 3; i++){
//			define la cantidad de llamadas del proceso entre 3 y 8
			int numCalls = rn.nextInt(5) + 3;
			for(int x = 0; x < numCalls; x++){
				//genera las llamadas con una duracion entre 5 y 10 segundos
				dispatcher.dispatchCall(rn.nextInt(5) + 5);
			}
//			espera entre 5 y 8 segundos antes de ejecutar el siguiente proceso de llamadas
			TimeUnit.SECONDS.sleep(rn.nextInt(3) + 5);
		}
//		espera 10 segundos para que finalicen todas las llamadas
		TimeUnit.SECONDS.sleep(10);
		dispatcher.stop();
    }
}

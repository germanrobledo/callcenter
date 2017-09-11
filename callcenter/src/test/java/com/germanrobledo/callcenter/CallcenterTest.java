package com.germanrobledo.callcenter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.germanrobledo.callcenter.CallHandler;
import com.germanrobledo.callcenter.CallHandlerEnum;
import com.germanrobledo.callcenter.CallHandlerQueue;
import com.germanrobledo.callcenter.Dispatcher;

/**
 * Esta clase implementa los tres test de la aplicacion 
 * tenThreadTest , moreCallsThanThreadsTest , moreCallsThanHandlersTest
 * 
 */
public class CallcenterTest {

	private CallHandlerQueue queue;
	private Dispatcher dispatcher;
	private int secondsToShutdown = 20;
	
	/**
	 * Inicializa la cola de operadores de llamada con 1 director , 2 supervisores y 3 operadores
	 * e inicializa el Dispatcher antes de cada Test
	 */
	@Before
	public void startCallcenter() throws InterruptedException{
		queue = new CallHandlerQueue();
		queue.put(new CallHandler(CallHandlerEnum.Director, "Pedro"));
		queue.put(new CallHandler(CallHandlerEnum.Supervisor, "Martin"));
		queue.put(new CallHandler(CallHandlerEnum.Supervisor, "Lucas"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Pablo"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Miguel"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Fernando"));
		dispatcher = new Dispatcher(queue);
	}

	/**
	 * Espera secondsToShutdown segundos y luego detiene al dispatcher cuando finaliza
	 * cada Test
	 */
	@After
	public void shutdownCallcenter() throws InterruptedException{
		TimeUnit.SECONDS.sleep(secondsToShutdown);
		dispatcher.stop();
	}
	
	/**
	 * tenThreadsTest ejecuta diez llamadas en forma simultanea (pedido en el enunciado)
	 * agrega 4 operadores mas a la cola para poder atende todas las llamadas a la vez
	 */
	@Test
	public void tenThreadsTest() throws InterruptedException{
		System.out.println("Comienza tenThreadTest:");
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Jose"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Mario"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Leonardo"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Matias"));
		Random rn = new Random();
		for(int i = 0; i < 10; i++){
			dispatcher.dispatchCall(rn.nextInt(5) + 5);
		}
	}
	
	/**
	 * moreCallsThanThreadsTest ejecuta 12 llamadas en forma simultanea con el fin de
	 * mostrar como se encolan las llamadas cuando son mas que los threads disponibles (10)
	 * al comenzar el test se agregan 6 operadores mas a la cola para poder atender los 12 llamados a la vez
	 */
	@Test
	public void moreCallsThanThreadsTest() throws InterruptedException{
		System.out.println("Comienza moreCallsThanThreadsTest:");
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Jose"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Mario"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Leonardo"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Matias"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Juan"));
		queue.put(new CallHandler(CallHandlerEnum.Operator, "Ariel"));
		
		Random rn = new Random();
		for(int i = 0; i < 12; i++){
			dispatcher.dispatchCall(rn.nextInt(5) + 5);
		}
		secondsToShutdown = 30;
	}
	
	/**
	 * moreCallsThanHandlersTest ejecuta 15 llamadas en forma simultanea con solo 6 operadores disponibles
	 * con el fin de demostrar  como se bloquean las llamadas cuando no hay operadores disponibles
	 * hasta que se libere uno.
	 */
	@Test
	public void moreCallsThanHandlersTest(){
		System.out.println("Comienza moreCallsThanHandlersTest:");
		Random rn = new Random();
		for(int i = 0; i < 15; i++){
			dispatcher.dispatchCall(rn.nextInt(5) + 5);
		}
		secondsToShutdown = 30;
	}

}

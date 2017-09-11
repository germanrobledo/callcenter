package com.germanrobledo.callcenter;

import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * La clase CallHandlerQueue modela la cola de operadores de llamada
 * Utiliza un LinkedBlockingQueue para bloquear los threads cuando no hay operadores en la cola
 * e implementa su propia version del put que inserta en la cola los operadores dependiendo de su tipo
 * ya que primero debe tomar  los operadores libres luego los supervisores y finalmente los directores 
 */

public class CallHandlerQueue {

	private LinkedBlockingDeque<CallHandler> deque;
	
	public CallHandlerQueue(){
		deque = new LinkedBlockingDeque<CallHandler>();
	}
	public CallHandler take() throws InterruptedException{
		return this.deque.take();
	}
	
	public synchronized void put(CallHandler op) throws InterruptedException{
		switch (op.getType()){
		case Operator:
			//Si es de tipo Operador lo inserta al principio de la cola
			this.deque.putFirst(op);
			break;
		case Director:
			//Si es de tipo Director lo inserta ultimo ya que solo los debe tomar si ya no quedan
			//ni operadores ni supervisores
			this.deque.putLast(op);
			break;
		case Supervisor:
			//Si es de tipo Supervisor saca de la cola todos Operadores, los inserta en una pila
			//inserta en la cola el supervisor y luego todos los operadores que este en la pila
			//utiliza una pila para mantener el orden original de los operadores
			CallHandler first = this.deque.peekFirst();
			Stack<CallHandler> stack = new Stack<>();
			while (first != null && first.getType() == CallHandlerEnum.Operator){
				stack.push(this.deque.pollFirst());
				first = this.deque.peekFirst();
			}
			this.deque.putFirst(op);
			while(!stack.isEmpty()){
				this.deque.putFirst(stack.pop());
			}
			break;
			
		}
	
	}
}

package TDACola;

import Exceptions.EmptyQueueException;
import TDAPila.Nodo;
public class LinkedQueue<E> implements Queue<E>{
	
	protected int size;
	protected Nodo<E> head, tail;
	
	public LinkedQueue() {
		size = 0;
		head = new Nodo<E>();
		tail = new Nodo<E>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E front() throws EmptyQueueException {
		if(size == 0) throw new EmptyQueueException("Cola vacía");
		return head.getElement();
	}

	@Override
	public void enqueue(E element) {
		Nodo<E> nuevo = new Nodo<E>(element,null);
		if(size == 0)
			head = nuevo;
		else
			tail.setSiguiente(nuevo);
		tail = nuevo;
		size++;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(size == 0) throw new EmptyQueueException("Cola vacía");
		E toRet = head.getElement();
		head = head.getSiguiente();
		size--;
		if(size == 0)
			tail = null;
		return toRet;
	}

}

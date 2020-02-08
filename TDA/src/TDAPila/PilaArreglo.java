package TDAPila;

import Exceptions.*;

public class PilaArreglo<E> implements Stack<E> {
	
	protected int size;
	protected E [] datos;
	
	public PilaArreglo(int N) {
		size = 0;
		datos = (E[]) new Object[N];
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
	public E top() throws EmptyStackException {
		if(size == 0) throw new EmptyStackException("Pila vacía");
		return datos[size-1];
	}

	@Override
	public void push(E element) {
		if(size == datos.length)
			try {
				throw new FullStackException("Pila llena");
			} catch (FullStackException e) {
				e.printStackTrace();
			}
		datos[size] = element;
		size++;
	}

	@Override
	public E pop() throws EmptyStackException {
		if(size == 0) throw new EmptyStackException("Pila vacía");
		E toRet = datos[size-1];
		datos[size-1] = null;
		size--;
		return toRet;
	}

}

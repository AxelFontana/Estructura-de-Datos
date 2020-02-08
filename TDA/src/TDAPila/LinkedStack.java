package TDAPila;
import Exceptions.*;

public class LinkedStack<E> implements Stack<E> {
	protected Nodo<E> top;
	protected int size;
	
	public LinkedStack() {
		top = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	public void push(E elem) {
		Nodo<E> nuevo = new Nodo<E>(elem,top);
		top = nuevo;
		size++;
	}
	
	public E top() throws EmptyStackException{
		if(isEmpty()) throw new EmptyStackException("Pila vacía");
		else
			return top.getElement();
	}
	
	public E pop() throws EmptyStackException{
		if(isEmpty()) throw new EmptyStackException("Pila vacía");
		else {
			E temp = top.getElement();
			top = top.getSiguiente();
			size--;
			return temp;
		}
	}
}


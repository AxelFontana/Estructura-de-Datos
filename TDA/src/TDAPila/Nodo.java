package TDAPila;

public class Nodo<E> {
	private E elemento;
	private Nodo<E> siguiente;
	
	public Nodo() {
		this(null,null);
	}
	
	public Nodo(E e, Nodo<E> s) {
		elemento = e;
		siguiente = s;
	}
	
	public E getElement() {
		return elemento;
	}
	
	public Nodo<E> getSiguiente(){
		return siguiente;
	}
	
	public void setElement(E elem) {
		elemento = elem;
	}
	
	public void setSiguiente(Nodo<E> s) {
		siguiente = s;
	}
} 



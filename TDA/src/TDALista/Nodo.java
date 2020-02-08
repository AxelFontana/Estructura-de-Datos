package TDALista;

public class Nodo<E> implements Position<E> {
	private E elemento;
	private Nodo<E> siguiente;
	
	public Nodo(E elemento) {
		this(elemento,null);
	}
	
	public Nodo(E e, Nodo<E> s) {
		elemento = e;
		siguiente = s;
	}
	
	public E getElement() {
		return elemento;
	}
	
	public E element() {
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

package TDALista;

public class NodoD<E> implements Position<E> {
	private NodoD<E> prev, next;
	private E element;
	
	public NodoD(NodoD<E> p, NodoD<E> n, E e) {
		prev = p;
		next = n;
		element = e;
	}
	
	public E element() {
		return element;
	}
	
	public NodoD<E> getNext(){
		return next;
	}
	
	public NodoD<E> getPrev() {
		return prev;
	}
	
	public void setNext(NodoD<E> n) {
		next = n;
	}
	
	public void setPrev(NodoD<E> p) {
		prev = p;
	}
	
	public void setEelement(E e) {
		element = e;
	}
}

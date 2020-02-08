package TDATree;
import TDALista.*;

public class TNodo<E> implements Position<E> {
	private E elemento;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;
	
	public TNodo(E elem, TNodo<E> p) {
		elemento = elem;
		padre = p;
		hijos = new DoubleLinkedList<TNodo<E>>();
	}
	
	public TNodo(E elem){
		this(elem, null);
	}
	
	public E element() {
		return elemento;
	}
	
	public PositionList<TNodo<E>> getHijos(){
		return hijos;
	}
	
	public void setElemento(E elem) {
		elemento = elem;
	}
	
	public TNodo<E> getPadre(){
		return padre;
	}
	
	public void setPadre(TNodo<E> p) {
		padre = p;
	}
}

  package TDALista;

import java.util.Iterator;

import Excepciones.*;
public class ListaDobleEnlaze<E> implements PositionList<E> {
	
	protected Nodo<E> Inicial,Final;
	protected int size;
	public ListaDobleEnlaze() {
		Final=new Nodo(null,null,null);
		Inicial=new Nodo(null,null,null);
		Inicial.setNext(Final);
		Final.setPrev(Inicial);
		size=0;
	}
	private Nodo<E> checkPosition(Position<E>p)throws InvalidPositionException{
		if (p==null||p==Inicial||p==Final) {
			throw new InvalidPositionException("Posicion invalida");
			}
		Nodo<E>ret=null;
		try {
			ret=(Nodo<E>)p;
			}
		catch (ClassCastException e) {
			throw new InvalidPositionException("Posicion Invalida");
		}
		return ret;
	}
	public int size() {
		return size;
	}

	/**
	 * Consulta si la lista est� vac�a.
	 * @return Verdadero si la lista est� vac�a, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return (size==0);
			
	}

	/**
	 * Devuelve la posici�n del primer elemento de la lista. 
	 * @return Posici�n del primer elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 */
	public Position<E> first() throws EmptyListException{		
		if (isEmpty()) {
			throw new EmptyListException("Lista Vacia");
		}
		return Inicial.getNext();		
	}
	/**
	 * Devuelve la posici�n del �ltimo elemento de la lista. 
	 * @return Posici�n del �ltimo elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 * 
	 */
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) {
			throw new EmptyListException("Lista Vacia");
		}
		return Final.getPrev(); 
	}

	/**
	 * Devuelve la posici�n del elemento siguiente a la posici�n pasada por par�metro.
	 * @param p Posici�n a obtener su elemento siguiente.
	 * @return Posici�n del elemento siguiente a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si el posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al �ltimo elemento de la lista.
	 */
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> nodo = checkPosition(p);
		if (isEmpty()) {
			throw new InvalidPositionException("Lista Vacia");
		}
		
		if(nodo==Final.getPrev()) {
			throw new BoundaryViolationException("Caes Fuera de la lista");
		}		
		return nodo.getNext();
	}
	/**
	 * Devuelve la posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @param p Posici�n a obtener su elemento anterior.
	 * @return Posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al primer elemento de la lista.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> nodo = checkPosition(p);
		if (isEmpty()) {
			throw new InvalidPositionException("Lista Vacia");
		}	
		if(nodo==Inicial.getNext()) {
			throw new BoundaryViolationException("Caes fuera de la lista");
		}
		return nodo.getPrev();
	}

	/**
	 * Inserta un elemento al principio de la lista.
	 * @param element Elemento a insertar al principio de la lista.
	 */
	public void addFirst(E element) {
		Nodo<E> nuevo = new Nodo<E>(element,null,null);
		Nodo<E> next,prev;
		//begin diferente
		prev = Inicial;
		next = prev.getNext();
		
		//end diferente
		next.setPrev(nuevo);
		prev.setNext(nuevo);
		nuevo.setPrev(prev);
		nuevo.setNext(next);
		size++;
	}  

	/**
	 * Inserta un elemento al final de la lista.
	 * @param element Elemento a insertar al final de la lista.
	 */
	public void addLast(E element) {
		Nodo<E> nuevo = new Nodo<E>(element,null,null);
		Nodo<E> next,prev;
		//begin diferente
		next = Final;
		prev = Final.getPrev();
		//end diferente
		next.setPrev(nuevo);
		prev.setNext(nuevo);
		nuevo.setPrev(prev);
		nuevo.setNext(next);
		size++;
	}
	/**
	 * Inserta un elemento luego de la posici�n pasada por par�matro.
	 * @param p Posici�n en cuya posici�n siguiente se insertar� el elemento pasado por par�metro.
	 * @param element Elemento a insertar luego de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addAfter(Position<E> p, E element) throws InvalidPositionException{
		if(isEmpty()) {
			throw new InvalidPositionException("PosicionInvalida");
		}
		Nodo<E> nuevo=new Nodo<E>(element,null,null);
		Nodo<E>next,prev;
		prev=checkPosition(p);
		next=prev.getNext();
		next.setPrev(nuevo);
		prev.setNext(nuevo);
		nuevo.setNext(next);
		nuevo.setPrev(prev);
		size++;
	}

	/**
	 * Inserta un elemento antes de la posici�n pasada como par�metro.
	 * @param p Posici�n en cuya posici�n anterior se insertar� el elemento pasado por par�metro. 
	 * @param element Elemento a insertar antes de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addBefore(Position<E> p, E element) throws InvalidPositionException{
		if(isEmpty()) {
			throw new InvalidPositionException("PosicionInvalida");
		}
		Nodo<E> nuevo=new Nodo<E>(element,null,null);
		Nodo<E>next,prev;
		next=checkPosition(p);
		prev=next.getPrev();
		next.setPrev(nuevo);
		prev.setNext(nuevo);
		nuevo.setNext(next);
		nuevo.setPrev(prev);
		size++;
	}
	/**
	 * Remueve el elemento que se encuentra en la posici�n pasada por par�metro.
	 * @param p Posici�n del elemento a eliminar.
	 * @return element Elemento removido.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */	
	public E remove(Position<E> p) throws InvalidPositionException{
		if (isEmpty()) {
			throw new InvalidPositionException("Lista Vacia");
		}
		Nodo<E>aux=checkPosition(p);
		Nodo<E>next,prev;
		next=aux.getNext();
		prev=aux.getPrev();
		next.setPrev(prev);
		prev.setNext(next);
		E ret=aux.element();
		aux.setNext(null);
		aux.setPrev(null);
		aux.setElement(null);
		size--;
		return ret;
	}

	/**
	
	 * Establece el elemento en la posici�n pasados por par�metro. Reemplaza el elemento que se encontraba anteriormente en esa posici�n y devuelve el elemento anterior.
	 * @param p Posici�n a establecer el elemento pasado por par�metro.
	 * @param element Elemento a establecer en la posici�n pasada por par�metro.
	 * @return Elemento anterior.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.	 
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException{
		if (isEmpty()) {
			throw new InvalidPositionException("Lista Vacia");
		}
		Nodo<E>aux=checkPosition(p);
		E elemento=aux.element();
		aux.setElement(element);
		return elemento;
	}
	/**
	 * Devuelve un un iterador de todos los elementos de la lista.
	 * @return Un iterador de todos los elementos de la lista.
	 */
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	public Iterable<Position<E>> positions() {
	PositionList<Position<E>> lista = new ListaDobleEnlaze<Position<E>>();
	Nodo<E> p = Inicial.getNext();
	while(p!=Final){
		lista.addLast(p);
		p = p.getNext();
	}
	return lista;
}
	}

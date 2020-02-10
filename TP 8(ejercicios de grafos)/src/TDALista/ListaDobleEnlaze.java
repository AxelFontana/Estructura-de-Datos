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
	 * Consulta si la lista está vacía.
	 * @return Verdadero si la lista está vacía, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return (size==0);
			
	}

	/**
	 * Devuelve la posición del primer elemento de la lista. 
	 * @return Posición del primer elemento de la lista.
	 * @throws EmptyListException si la lista está vacía.
	 */
	public Position<E> first() throws EmptyListException{		
		if (isEmpty()) {
			throw new EmptyListException("Lista Vacia");
		}
		return Inicial.getNext();		
	}
	/**
	 * Devuelve la posición del último elemento de la lista. 
	 * @return Posición del último elemento de la lista.
	 * @throws EmptyListException si la lista está vacía.
	 * 
	 */
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) {
			throw new EmptyListException("Lista Vacia");
		}
		return Final.getPrev(); 
	}

	/**
	 * Devuelve la posición del elemento siguiente a la posición pasada por parámetro.
	 * @param p Posición a obtener su elemento siguiente.
	 * @return Posición del elemento siguiente a la posición pasada por parámetro.
	 * @throws InvalidPositionException si el posición pasada por parámetro es inválida o la lista está vacía.
	 * @throws BoundaryViolationException si la posición pasada por parámetro corresponde al último elemento de la lista.
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
	 * Devuelve la posición del elemento anterior a la posición pasada por parámetro.
	 * @param p Posición a obtener su elemento anterior.
	 * @return Posición del elemento anterior a la posición pasada por parámetro.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o la lista está vacía.
	 * @throws BoundaryViolationException si la posición pasada por parámetro corresponde al primer elemento de la lista.
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
	 * Inserta un elemento luego de la posición pasada por parámatro.
	 * @param p Posición en cuya posición siguiente se insertará el elemento pasado por parámetro.
	 * @param element Elemento a insertar luego de la posición pasada como parámetro.
	 * @throws InvalidPositionException si la posición es inválida o la lista está vacía.
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
	 * Inserta un elemento antes de la posición pasada como parámetro.
	 * @param p Posición en cuya posición anterior se insertará el elemento pasado por parámetro. 
	 * @param element Elemento a insertar antes de la posición pasada como parámetro.
	 * @throws InvalidPositionException si la posición es inválida o la lista está vacía.
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
	 * Remueve el elemento que se encuentra en la posición pasada por parámetro.
	 * @param p Posición del elemento a eliminar.
	 * @return element Elemento removido.
	 * @throws InvalidPositionException si la posición es inválida o la lista está vacía.
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
	
	 * Establece el elemento en la posición pasados por parámetro. Reemplaza el elemento que se encontraba anteriormente en esa posición y devuelve el elemento anterior.
	 * @param p Posición a establecer el elemento pasado por parámetro.
	 * @param element Elemento a establecer en la posición pasada por parámetro.
	 * @return Elemento anterior.
	 * @throws InvalidPositionException si la posición es inválida o la lista está vacía.	 
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

package TDALista;


import java.util.Iterator;

import Exceptions.*;


public class ListaCircular<E> implements PositionList<E> {
	protected Nodo<E> cabeza;
	protected int size;
	
	public ListaCircular() {
		cabeza = new Nodo<E>(null);
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacía");
		return cabeza;
	}
	
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacía");
		else {
			Nodo<E> p = cabeza;
			while(p.getSiguiente() != cabeza) {
				p = p.getSiguiente();
			}
		return p;
		}
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		if(isEmpty()) throw new InvalidPositionException("Lista vacia");
		Nodo<E> n = checkPosition(p);
		Position<E> toRet = null;
		try {
			if(p == last()) toRet = first();
			else toRet = n.getSiguiente();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		
		return toRet;
	}
	
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		try {
			if(p==null) throw new InvalidPositionException("Posición nula");
			else {
				return(Nodo<E>) p;
			}
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException(e.getMessage());
		}
	}
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> aux = null;
		if(isEmpty()) throw new InvalidPositionException("Posición inválida");
		try {
			if(p == first()) throw new BoundaryViolationException("Primera posición");
			
			Nodo<E> n = checkPosition(p);
			aux = cabeza;
			while(aux.getSiguiente() != n && aux.getSiguiente() != cabeza) {
				aux = aux.getSiguiente();
			}	
			
		}
		catch(EmptyListException e) {
			if(aux.getSiguiente() == cabeza) throw new InvalidPositionException("Posición inválida");
			System.out.println(e.getMessage());
		}
	return aux;
	}
	
	public void addFirst(E e) {
		Nodo<E> nuevo = new Nodo<E>(e);

		if(isEmpty()) {
			nuevo.setSiguiente(nuevo);
		}
		else {
			try {
				Nodo<E> ultimo = checkPosition(last());
				ultimo.setSiguiente(nuevo);
				nuevo.setSiguiente(cabeza);
			}
			catch(EmptyListException|InvalidPositionException el) {
				System.out.println(el.getMessage());
			}
		}
		cabeza = nuevo;
		size++;
	}
	
	public void addLast(E e) {
		if(isEmpty())
			addFirst(e);
		else {
			Nodo<E> nuevo = new Nodo<E>(e, cabeza);
			Nodo<E> aux = cabeza;
			while(aux.getSiguiente() != cabeza) {
				aux = aux.getSiguiente();
			}
			aux.setSiguiente(nuevo);
			size++;
		}
	}
	
	public void addAfter(Position<E> p, E e) throws InvalidPositionException {
		Nodo<E> nuevo = new Nodo<E>(e);
		Nodo<E> n = checkPosition(p);
		nuevo.setSiguiente(n.getSiguiente());
		n.setSiguiente(nuevo);
	}
	
	public void addBefore(Position<E> p, E e) throws InvalidPositionException{
		if(isEmpty()) throw new InvalidPositionException("Posicion invalida");
		try {
			if(p == first())
				addFirst(e);
			else {
				Nodo<E> n = checkPosition(p);
				Nodo<E> nuevo = new Nodo<E>(e,n);
				Nodo<E> anterior = (Nodo<E>) prev(p);
				anterior.setSiguiente(nuevo);
			}
		}
		catch(EmptyListException|BoundaryViolationException el) {
			System.out.println(el.getMessage());
		}
	}
	
	public E remove(Position<E> p) throws InvalidPositionException{
		if(isEmpty()) throw new InvalidPositionException("Posición inválida");
		Nodo<E> n = checkPosition(p);
		E toReturn = n.element();
		if(n == cabeza)
			cabeza = cabeza.getSiguiente();
		else {
			try {
				Nodo<E> anterior = (Nodo<E>) prev(p);
				anterior.setSiguiente(n.getSiguiente());
			}
			catch(InvalidPositionException|BoundaryViolationException e) {
				System.out.println(e.getMessage());
			}
		}
		size--;
		return toReturn;
	}
	
	public E set(Position<E> p, E e) throws InvalidPositionException{
		if(isEmpty()) throw new InvalidPositionException("Posición inválida");
		Nodo<E> n = checkPosition(p);
		E toReturn = n.element();
		n.setElement(e);
		return toReturn;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementoIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> P = new ListaSimplementeEnlazada<Position<E>>();
		try {
			Position<E> p;
			if(!this.isEmpty()) {
				p = this.first();
			}
			else
				p = null;
			while(p!=null) {
				P.addLast(p);
				if(p == this.last())
					p = null;
				else
					p = this.next(p);
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	return P;
	}
}

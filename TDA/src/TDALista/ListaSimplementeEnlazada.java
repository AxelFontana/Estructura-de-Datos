package TDALista;

import java.util.Iterator;


import Exceptions.*;

public class ListaSimplementeEnlazada<E> implements PositionList<E> {
	protected Nodo<E> cabeza;
	protected int size;
	
	public ListaSimplementeEnlazada() {
		cabeza = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return cabeza == null;
	}
	
	public void addFirst(E e) {
		cabeza = new Nodo<E>(e,cabeza);
		size++;
	}
	
	public void addLast(E e) {
		if(isEmpty())
			addFirst(e);
		else {
			Nodo<E> p = cabeza;
			while(p.getSiguiente() != null) {
				p = p.getSiguiente();
			}
			p.setSiguiente(new Nodo<E>(e));
			size++;
		}
	}
	
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vac�a");
		else
			return cabeza;
	}
	
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vac�a");
		else {
			Nodo<E> p = cabeza;
			while(p.getSiguiente() != null) {
				p = p.getSiguiente();
			}
			
			return p;
		}
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> n = checkPosition(p);
		if(n.getSiguiente() == null) throw new BoundaryViolationException("No existe la posici�n");
		else
			return n.getSiguiente();
	}
	
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		try {
			if(p==null) throw new InvalidPositionException("Posici�n nula");
			else {
				return(Nodo<E>) p;
			}
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException(e.getMessage());
		}
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		try {
		if(p==first()) throw new BoundaryViolationException("Lista::prev()"+"posici�n primera");
		}
		catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
		Nodo<E> n = checkPosition(p);
		Nodo<E> aux = cabeza;
		while(aux.getSiguiente() != n && aux.getSiguiente() != null) {
			aux = aux.getSiguiente();
		}
		if(aux.getSiguiente() == null) throw new InvalidPositionException("Lista::prev()"+"Posici�n no pertenece a la lista");
		return aux;
		}
	
	public void addAfter(Position<E> p, E e) throws InvalidPositionException{
		Nodo<E> n = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(e);
		nuevo.setSiguiente(n.getSiguiente());
		n.setSiguiente(nuevo);
		size++;
	}
	
	public void addBefore(Position<E> p, E e) throws InvalidPositionException{
		Nodo<E> n = checkPosition(p);
		try {
			if(p==first())
				addFirst(e);
			else {
				Nodo<E> anterior = (Nodo<E>) prev(p);
				Nodo<E> nuevo = new Nodo<E>(e,n);
				anterior.setSiguiente(nuevo);
				}
		}
		catch(EmptyListException|InvalidPositionException|BoundaryViolationException ele) {
			System.out.println(ele.getMessage());
		}
	}
	
	public E remove(Position<E> p) throws InvalidPositionException{
		if(isEmpty()) throw new InvalidPositionException("Posici�n inv�lida");
			Nodo<E> n = checkPosition(p);
			E aux = p.element();
			if(n == cabeza)
				cabeza = cabeza.getSiguiente();
			else {
				try {
					Nodo<E> anterior = (Nodo<E>) prev(p);
					anterior.setSiguiente(n.getSiguiente());
				}
				catch(InvalidPositionException|BoundaryViolationException ele){
					System.out.println(ele.getMessage());
				}
			}
		size--;
		return aux;
	}

	public E set(Position<E> p, E e) throws InvalidPositionException {
		Nodo<E> n = checkPosition(p);
		E aux = n.getElement();
		n.setElement(e);
		return aux;
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
	
	public void invertir() {
		Position<E> p1, p2;
		boolean cortar = false;
		try {
			if(size>=2) {
				p1 = first();
				p2 = last();
				while(!cortar && p1 != p2) {
					E aux = p1.element();
					set(p1, p2.element());
					set(p2, aux);
					p1 = next(p1);
					if(p1 == p2)
						cortar = true;
					else
						p2 = prev(p2);
				}
			}
		}
		catch(InvalidPositionException|BoundaryViolationException|EmptyListException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public PositionList<E> clone(){
		PositionList<E> nueva = new ListaSimplementeEnlazada<E>();
		Nodo<E> nuevo = new Nodo<E>(null);
		try {
			Position<E> aux = this.last();
			while(aux != null) {
				nuevo.setElement(aux.element());
				nueva.addFirst(nuevo.element());
				if(aux == this.first()) {
					aux = null;
				}
				else {
					aux = this.prev(aux);
				}
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return nueva;
	}
} 
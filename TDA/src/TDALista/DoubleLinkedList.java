package TDALista;


import java.util.Iterator;

import Exceptions.*;
public class DoubleLinkedList<E> implements PositionList<E> {
	protected int size;
	protected NodoD<E> header, trailer;
	
	public DoubleLinkedList() {
		size = 0;
		header = new NodoD<E>(null,null,null);
		trailer =new  NodoD<E>(null,null,null);
		header.setNext(trailer);
		trailer.setPrev(header);
	}
	
	protected NodoD<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if(p==null) throw new InvalidPositionException("Pos nula");
		if(p==header) throw new InvalidPositionException("Posición header no válida");
		if(p==trailer) throw new InvalidPositionException("Posición trailer no válida");
		try {
			NodoD<E> temp = (NodoD<E>) p;
			if(temp.getPrev() == null || temp.getNext() == null) throw new InvalidPositionException("Posición no pertenece a lista válida");
			return temp;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("Posición no válida");
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacía");
		return header.getNext();
	}
	
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacía");
		return trailer.getPrev();
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		NodoD<E> n = checkPosition(p);
		NodoD<E> anterior = n.getPrev();
		if(anterior == header || n == header) throw new BoundaryViolationException("Posición primera, no tiene anterior");
		return anterior;
			
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		NodoD<E> n = checkPosition(p);
		NodoD<E> siguiente = n.getNext();
		if(siguiente == trailer || n == trailer) throw new BoundaryViolationException("Posición última, no tiene siguiente");
		return siguiente;
	}
	
	public void addBefore(Position<E> p, E e) throws InvalidPositionException{
		NodoD<E> sig = checkPosition(p);
		NodoD<E> ant = sig.getPrev();
		NodoD<E> nuevo = new NodoD<E>(null, null, e);
		nuevo.setNext(sig);
		nuevo.setPrev(ant);
		ant.setNext(nuevo);
		sig.setPrev(nuevo);
		size++;
	}
	
	public void addAfter(Position<E> p, E e) throws InvalidPositionException{
		NodoD<E> ant = checkPosition(p);
		NodoD<E> sig = ant.getNext();
		NodoD<E> nuevo = new NodoD<E>(null, null, e);
		nuevo.setPrev(ant);
		nuevo.setNext(sig);
		sig.setPrev(nuevo);
		ant.setNext(nuevo);
		size++;
	}
	
	public void addFirst(E e) {
		NodoD<E> nuevo = new NodoD<E>(null, null, e);
		NodoD<E> ant = header;
		NodoD<E> sig = header.getNext();
		nuevo.setNext(sig);
		nuevo.setPrev(ant);
		ant.setNext(nuevo);
		sig.setPrev(nuevo);
		size++;
	}
	
	public void addLast(E e) {
		NodoD<E> nuevo = new NodoD<E>(null, null, e);
		NodoD<E> sig = trailer;
		NodoD<E> ant = trailer.getPrev();
		nuevo.setNext(sig);
		nuevo.setPrev(ant);
		sig.setPrev(nuevo);
		ant.setNext(nuevo);
		size++;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException{
		NodoD<E> n = checkPosition(p);
		E toReturn = n.element();
		NodoD<E> nPrev = n.getPrev();
		NodoD<E> nNext = n.getNext();
		nPrev.setNext(nNext);
		nNext.setPrev(nPrev);
		size--;
		n.setNext(null);
		n.setPrev(null);
		n.setEelement(null);
		return toReturn;
	}
	
	public E set(Position<E> p, E e) throws InvalidPositionException{
		if(isEmpty()) throw new InvalidPositionException("La posición no existe");
		NodoD<E> n = checkPosition(p);
		E toReturn = n.element();
		n.setEelement(e);
		return toReturn;
	}
	
	public Iterator<E> iterator() {
		return new ElementoIterator<E>(this);
		
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> P = new DoubleLinkedList<Position<E>>();
		
			NodoD<E> p;
			if(!isEmpty())
				p = header.getNext();
			else
				p = null;	
			while(p!=null) {
				P.addLast(p);	
				if(p==trailer.getPrev())
					p = null;
				else
					p = p.getNext() ;
				}
	return P;
	}
	
	public void elminarAyB(E A, E B) {
		if(size>1) {
			NodoD<E> n1,n2;
			n1 = header.getNext();
			n2 = n1.getNext();
			boolean salir = false;
			while(n2!=trailer && !salir) {
				if(n1.element().equals(A) && n2.element().equals(B)) {
					NodoD<E> salvar = n2.getNext();
					n1.getPrev().setNext(n2.getNext());
					n2.getNext().setPrev(n1.getPrev());
					size-=2;
					if(salvar == trailer)
						salir = true;
					else {
						n1 = salvar;
						n2 = salvar.getNext();
					}
				}
				else {
					n1 = n2;
					n2 = n1.getNext();
				}
			}
		}
	}
	
	public void eliminarA (PositionList<E> B) {
		int cant1, cant2;
		NodoD<E> aux = header;
		try {
			while(aux.getNext() != trailer) {
				aux = aux.getNext();
				cant1 = 0;
				cant2 = 0;
				for(Position<E> p: this.positions()) {
					if(p.element().equals(aux.element()))
						cant1++;
				}
				for(Position<E> p: B.positions()) {
					if(p.element().equals(aux.element()))
						cant2++;
				}
			
				if(cant2>cant1) {
					for(Position<E> p: this.positions()) {
						if(p.element().equals(aux.element()))
							this.remove(p);				
					}
				}
			}
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void modifica(PositionList<E> B) {
		if(!B.isEmpty()) {
			int cont = 1;
			NodoD<E> aux = header.getNext();
			while(aux!=trailer) {
				if(cont%2==0 && pertenece(aux.element(),B)) {
					NodoD<E> aElim = aux;
					aux = aux.getNext();
					NodoD<E> ant = aElim.getPrev();
					ant.setNext(aux);
					aux.setPrev(ant);
					size--;
					aElim.setEelement(null);
					aElim.setNext(null);
					aElim.setPrev(null);
				}
				else {
					aux = aux.getNext();
				}
				cont++;
			}
		}
	}
	
	private boolean pertenece(E e, PositionList<E> B) {
		boolean pert = false;
		try {
			Position<E> p = B.first();
			while(p!=null && !pert)
			{
				if(p.element() == e)
					pert = true;
				else {
					if(p == B.last())
						p = null;
					else
						p = B.next(p);
				}
			}
		}
		catch(EmptyListException|InvalidPositionException|BoundaryViolationException er) {
			er.printStackTrace();
		}
		return pert;
	}
}

package TDALista;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

public interface tp3ej5cInt<E> {
	public int size();
	public boolean isEmpty();
	public Position<E> first() throws EmptyListException;
	public Position<E> last() throws EmptyListException;
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	public void addFirst(E e);
	public void addLast(E e);
	public void addAfter(Position<E> p, E e) throws InvalidPositionException;
	public void addBefore(Position<E> p, E e) throws InvalidPositionException;
	public E remove(Position<E> p) throws InvalidPositionException;
	public E set(Position<E> p, E e) throws InvalidPositionException;
	public void invertir();
}

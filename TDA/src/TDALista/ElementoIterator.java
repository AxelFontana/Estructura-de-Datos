package TDALista;
import java.lang.*;
import java.util.*;
import java.util.NoSuchElementException;

import Exceptions.*;
public class ElementoIterator<E> implements Iterator<E> {
	protected PositionList<E> list;
	protected Position<E> cursor;
	
	public ElementoIterator(PositionList<E> l) {
		list = l;
		if(list.isEmpty())
			cursor = null;
		else
			try {
			cursor = list.first();
			}
		catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean hasNext() {
		return cursor!=null;
	}
	
	public E next() throws NoSuchElementException {
		if(cursor == null) throw new NoSuchElementException("No tiene siguiente");
		E toReturn = cursor.element();
		try {
		cursor = (cursor == list.last())? null : list.next(cursor);
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return toReturn;
	}
	
	public void remove() {
	}
} 

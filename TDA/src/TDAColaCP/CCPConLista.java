package TDAColaCP;

import Exceptions.*;
import TDALista.DoubleLinkedList;
import TDALista.Position;
import java.util.Comparator;

public class CCPConLista<K,V> implements PriorityQueue<K,V> {
	
	protected DoubleLinkedList<Entrada<K,V>> lista;
	protected Comparator<K> comp;
	
	public CCPConLista(Comparator<K> c) {
		lista = new DoubleLinkedList<Entrada<K,V>>();
		comp = c;
	}
	
	private class Entrada<Ke,Va> implements Entry<Ke,Va>{
		private Ke clave;
		private Va value;
		public Entrada(Ke clave, Va value) {
			this.clave = clave;
			this.value = value;
		}
		@Override
		public Ke getKey() {
			return clave;
		}

		@Override
		public Va getValue() {
			return value;
		}
		
		public String toString() {
			return "("+clave+","+value+")";
		}
		
	}

	@Override
	public int size() {
		return lista.size();
	}

	@Override
	public boolean isEmpty() {
		return lista.isEmpty();
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(isEmpty()) throw new EmptyPriorityQueueException("Cola con prioridad vacía");
		Entry<K,V> toRet = null;
		try {
			toRet = lista.first().element();
		}
		catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
		
		return toRet;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		Entrada<K,V> nueva = new Entrada<K,V>(key,value);
		if(isEmpty())
			lista.addFirst(nueva);
		else {
			try {
				Position<Entrada<K,V>> p = lista.first();
				boolean encontre = false;
				while(p!=null && !encontre) {
					if(comp.compare(p.element().getKey(), key) > 0) {
						lista.addBefore(p, nueva);
						encontre = true;
					}
					else {
						if(p == lista.last())
							p = null;
						else
							p = lista.next(p);
					}
				}
				if(!encontre)
					lista.addLast(nueva);
			}
			catch(InvalidPositionException|BoundaryViolationException|EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
		return nueva;
	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(isEmpty()) throw new EmptyPriorityQueueException("Cola con prioridad vacía");
		Entry<K,V> toRet = null;
		try {
				toRet = lista.remove(lista.first());
		}
		catch(EmptyListException |InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		
		return toRet;
	}
}

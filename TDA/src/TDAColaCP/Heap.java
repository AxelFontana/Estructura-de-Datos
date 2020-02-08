package TDAColaCP;

import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import java.util.Comparator;
public class Heap<K,V> implements PriorityQueue<K,V> {
	protected Entrada<K,V> [] elems;
	protected Comparator<K> comp;
	protected int size;
	
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
	
	public Heap(Comparator<K> comp) {
		elems = (Entrada<K,V> []) new Entrada [12];
		this.comp = comp;
		size = 0;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if(isEmpty()) throw new EmptyPriorityQueueException("Cola con prioridad vacía");
		return elems[1];
		
	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		Entrada<K,V> nueva = new Entrada<K,V>(key,value);
		
		if(size+1 == elems.length) {
			Entrada<K,V> [] nuevo = (Entrada<K,V>[]) new Entrada[elems.length*10];
			for(int i = 1; i <= size; i++) {
				nuevo[i] = elems[i];
			}
			elems = nuevo;
		}
		
		elems[++size] = nueva;
		int i = size;
		boolean seguir = true;
		while(i>1 && seguir) {
			Entrada<K,V> elemActual = elems[i];
			Entrada<K,V> elemPadre = elems[i/2];
			if(comp.compare(elemActual.getKey(), elemPadre.getKey()) < 0) {
				Entrada<K,V> aux = elems[i];
				elems[i] = elems[i/2];
				elems[i/2] = aux;
				i/=2;
			}
			else
				seguir = false;
		}
		
		return nueva;
	}

	@Override
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		if(isEmpty()) throw new EmptyPriorityQueueException("Cola con prioridad vacía");
		Entrada<K,V> toRet = elems[1];
		if(size == 1) {
			elems[1] = null;
			size = 0;
		}
		else {
			elems[1] = elems[size];
			elems[size] = null;
			size--;
			int i = 1;
			boolean seguir = true;
			while(seguir) {
				int hi = i*2;
				int hd = i*2+1;
				boolean tieneHijoIzquierdo = hi<=size();
				boolean tieneHijoDerecho = hd<=size();
				if(!tieneHijoIzquierdo)
					seguir = false;
				else {
					int m;
					if(tieneHijoDerecho) {
						if(comp.compare(elems[hi].getKey(), elems[hd].getKey()) < 0)
							m = hi;
						else
							m = hd;
					}
					else
						m = hi;
					if(comp.compare(elems[i].getKey(), elems[m].getKey()) > 0) {
						Entrada<K,V> aux = elems[i];
						elems[i] = elems[m];
						elems[m] = aux;
						i = m;
					}
					else
						seguir = false;
				}
			}
		}
		
		return toRet;
	}

}

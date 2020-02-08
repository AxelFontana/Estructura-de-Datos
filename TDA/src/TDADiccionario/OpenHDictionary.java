package TDADiccionario;

import Exceptions.*;
import TDALista.*;
import java.util.Iterator;
public class OpenHDictionary<K,V> implements Dictionary<K,V> {
	
	protected PositionList<Entrada<K,V>> [] A;
	protected int n;
	protected int N;
	protected static float factor = 0.9f;

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
		
		public void setKey(Ke k) {
			clave = k;
		}
		
		public void setValue(Va v) {
			value = v;
		}
		
	}
	
	public OpenHDictionary(int NN) {
		n = 0;
		N = NN;
		A = (PositionList<Entrada<K,V>> []) new PositionList[NN];
		for(int i = 0; i<NN; i++) {
			A[i] = new DoubleLinkedList<Entrada<K,V>>();
		}
	}
	
	public OpenHDictionary() {
		this(2);
	}

	@Override
	public int size() {
		return n;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	@Override
	public Entry<K,V> find(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException ("Clave inválida");
		Entry<K,V> toRet = null;
		int h = Math.abs(key.hashCode() % N);
		Iterator<Position<Entrada<K,V>>> it = A[h].positions().iterator();
		boolean encontre = false;
		Position<Entrada<K,V>> p;
		while(it.hasNext() && !encontre) {
			p = it.next();
			if(p.element().getKey().equals(key)) {
				toRet = p.element();
				encontre = true;
			}
		}
		
		return toRet;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException ("Clave inválida");
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		int h = Math.abs(key.hashCode() % N);
		for(Position<Entrada<K,V>> p : A[h].positions()) {
			if(p.element().getKey().equals(key))
				toRet.addLast(p.element());
		}
		
		return toRet;
	}
	
	protected void rehash() {
		int Nnueva = obtenerPrimo(N*2+1);
		PositionList<Entrada<K,V>> [] nueva = (PositionList<Entrada<K,V>> []) new DoubleLinkedList[Nnueva];
		for(int i = 0; i<Nnueva; i++) {
			nueva[i] = new DoubleLinkedList<Entrada<K,V>>();
		}
		
		for(int i = 0; i<N; i++) {
			for(Entrada<K,V> e: A[i]) {
				int h = Math.abs(e.getKey().hashCode() % Nnueva);
				nueva[h].addLast(e);
			}
		}
		
		A = nueva;
		N = Nnueva;
	}
	
	private int obtenerPrimo(int n) {
		int cont = n+1;
		boolean enc = false;
		while(!enc) {
			if(esPrimo(cont))
				enc = true;
			else
				cont++;
		}
		
		return cont;
	}
	
	private boolean esPrimo(int n) {
		int a;
		boolean enc = true;
		if(n!=2) {
			for(int i = 2; i<n && enc; i++) {
				a = n % i;
				if(a == 0)
					enc = false;
			}
		}
		return enc;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		if(n/N > factor)
			rehash();
		Entrada<K,V> nueva = new Entrada<K,V>(key,value);
		int h = Math.abs(key.hashCode() % N);
		A[h].addLast(nueva);
		n++;
		return nueva;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e == null) throw new InvalidEntryException("Entrada inválida");
		Entry<K,V> toRet = null;
			int h = Math.abs(e.getKey().hashCode() % N);
			if(h>N) throw new InvalidEntryException("Entrada inválida");
			Iterator<Position<Entrada<K,V>>> it = A[h].positions().iterator();
			boolean encontre = false;
			Position<Entrada<K,V>> pos;
			while(it.hasNext() && !encontre) {
				pos = it.next();
				if(pos.element().equals(e)) {
					encontre = true;
					try {
						toRet = A[h].remove(pos);
					} catch (InvalidPositionException e1) {
						e1.printStackTrace();
					}
					n--;
				}
			}
			if(!encontre) throw new InvalidEntryException("Entrada inválida, no pertenece al diccionario");
		return toRet;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		for(int i = 0; i<N; i++) {
			for(Position<Entrada<K,V>> p : A[i].positions()) {
				toRet.addLast(p.element());
			}
		}
		return toRet;
	}
}

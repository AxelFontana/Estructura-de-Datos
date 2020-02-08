package TDAMapeo;

import java.util.Iterator;

import Exceptions.*;
import TDALista.*;
import ImplementacionesListas.*;

public class OpenHMap<K,V> implements Map<K,V> {
	protected PositionList<Entrada<K,V>> [] A;
	protected int n; 
	protected int N;
	private static float factor = 0.9f;
	
	
	private class Entrada<Ke,Va> implements Entry<Ke,Va>{
		private Ke clave;
		private Va value;
		public Entrada(Ke clave, Va value) {
			this.clave = clave;
			this.value = value;
		}
		
		public void setValue(Va v) {
			value = v;
		}
		
		public void setKey(Ke k) {
			clave = k;
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
	
	public OpenHMap( int NN) {
		n = 0;
		N = NN;
		A = (PositionList<Entrada<K,V>> []) new PositionList[N];
		for(int i = 0; i<N; i++) {
			A[i] = new DoubleLinkedList<Entrada<K,V>>();
		}
	}
	
	public OpenHMap() {
		this(23);
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
	public int size() {
		return n;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		V toRet = null;
		if (key == null) throw new InvalidKeyException("Clave inválida");
		int h = Math.abs(key.hashCode() % N);
		Iterator<Position<Entrada<K,V>>> it = A[h].positions().iterator();
		Position<Entrada<K,V>> p;
		boolean encontre = false;
		while(it.hasNext() && !encontre) {
			p = it.next();
			if(p.element().getKey().equals(key)) {
				encontre = true;
				toRet = p.element().getValue();
			}
		}
		return toRet;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		V toRet = null;
		if(key == null) throw new InvalidKeyException("Clave inválida");
		if(n/N > factor)
			rehash();
		Entrada<K,V> nueva = new Entrada<K,V>(key, value);
		int h = Math.abs(key.hashCode() % N);
		if(A[h].isEmpty()) {
			A[h].addFirst(nueva);
			n++;
		}
		else {
			Iterator<Position<Entrada<K,V>>> it = A[h].positions().iterator();
			Position<Entrada<K,V>> p = null;
			boolean encontre = false;
			while(it.hasNext() && !encontre) {
				p = it.next();
				if(p.element().getKey().equals(key))
					encontre = true;
			}
			
			if(encontre) {
				toRet = p.element().getValue();
				p.element().setValue(value);
			}
			else {
				A[h].addLast(nueva);
				n++;
			}
		}
		
		return toRet;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		V toRet = null;
		if(key == null) throw new InvalidKeyException("Clave Inválida");
		try {
			int h = Math.abs(key.hashCode() % N);
			if(!A[h].isEmpty()) {
				Iterator<Position<Entrada<K,V>>> it = A[h].positions().iterator();
				Position<Entrada<K,V>> p = null;
				boolean encontre = false;
				while(it.hasNext() && !encontre) {
					p = it.next();
					if(p.element().getKey().equals(key)){
						encontre = true;
						toRet = p.element().getValue();
						A[h].remove(p);
						n--;
					}
				}
			}
		}
		catch(InvalidPositionException e) {
			e.printStackTrace();
		}
		
		return toRet;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> toRet = new DoubleLinkedList<K>();
		for(int i = 0; i<N; i++) {
			for(Entrada<K,V> e: A[i])
				toRet.addLast(e.getKey());
		}
		
		return toRet;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> toRet = new DoubleLinkedList<V>();
		for(int i = 0; i<N; i++) {
			for(Entrada<K,V> e: A[i])
				toRet.addLast(e.getValue());
		}
		return toRet;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		for(int i = 0; i<N; i++)
			for(Entrada<K,V> e: A[i])
				toRet.addLast(e);
		return toRet;
	}

}

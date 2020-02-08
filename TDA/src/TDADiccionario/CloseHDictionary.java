package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import TDALista.*;

public class CloseHDictionary<K,V> implements Dictionary<K,V>{
	protected Entrada<K,V> [] A;
	private final Entrada<K,V> available;
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
	
	public CloseHDictionary(int Nn) {
		available = new Entrada<K,V>(null,null);
		N = Nn;
		n = 0;
		A = (Entrada<K,V>[]) new Entrada[N];
	}
	
	public CloseHDictionary() {
		this(7);
	}
	
	protected void rehash() {
		int Nnueva = obtenerPrimo(N*2+1);
		Entrada<K,V> [] nueva = (Entrada<K,V> []) new Entrada[Nnueva];
		for(int i = 0; i<N; i++) {
			if(A[i] != null && A[i] != available) {
				int h = Math.abs(A[i].getKey().hashCode() % Nnueva);
				while(nueva[h] != null) {
					h = (h+1) % Nnueva;
				}
				nueva[h] = A[i];
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
		boolean enc = false;
		for(int i = 2; i<n && !enc; i++) {
			a = n % i;
			if(a == 0)
				enc = true;
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
	public Entry<K,V> find(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		Entry<K,V> toRet = null;
		int h = Math.abs(key.hashCode() % N);
		boolean encontre = false;
		while(A[h] != null && !encontre) {
			if(A[h] == available)
				h = (h+1) % N;
			else {
				if(A[h].getKey().equals(key)) {
					toRet = A[h];
					encontre = true;
				}
				else
					h = (h+1) % N;
			}
		}
		
		return toRet;
	}


	@Override
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		int h = Math.abs(key.hashCode() % N);
		while(A[h] != null) {
			if(A[h].getKey().equals(key))
				toRet.addLast(A[h]);
			h = (h+1) % N;
		}
		
		return toRet;
		
	}


	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave Inválida");
		if(n/N > factor)
			rehash();
		Entrada<K,V> nueva = new Entrada<K,V>(key,value);
		int h = Math.abs(key.hashCode() % N);
		boolean encontre = false;
		while(A[h] != null && !encontre) {
			if(A[h] == available) {
				A[h] = nueva;
				encontre = true;
			}
			else
				h = (h+1) % N;
		}
		
		if(!encontre)
			A[h] = nueva;
		
		n++;
		return nueva;
	}


	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e == null) throw new InvalidEntryException("Entrada inválida");
		Entry<K,V> toRet = null;
		int h = Math.abs(e.getKey().hashCode() % N);
		boolean encontre = false;
		while(A[h] != null && !encontre) {
			if(A[h] == available)
				h = (h+1) % N;
			else {
				if(A[h].getKey().equals(e.getKey())) {
					encontre = true;
					toRet = A[h];
					A[h] = available;
				}
				else
					h = (h+1) % N;
			}
		}
		
		if(!encontre) throw new InvalidEntryException("Entrada inválida");
		n--;
		return toRet;
	}


	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>>  toRet = new DoubleLinkedList<Entry<K,V>>();
		for(int i = 0; i<N; i++) {
			if(A[i] != null && !A[i].equals(available))
				toRet.addLast(A[i]);
		}
		return toRet;
	}
}

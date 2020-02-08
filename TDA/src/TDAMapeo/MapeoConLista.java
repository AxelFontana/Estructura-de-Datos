package TDAMapeo;
import Exceptions.*;
import TDALista.*;
import ImplementacionesListas.*;
public class MapeoConLista<K,V> implements Map<K,V>{
	protected PositionList<Entrada<K,V>> S;
	
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
	
	public MapeoConLista() {
		S = new DoubleLinkedList<Entrada<K,V>>();
	}

	@Override
	public int size() {
		return S.size();
	}

	@Override
	public boolean isEmpty() {
		return S.isEmpty();
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("Clave invalida");
		
		for(Position<Entrada<K,V>> p: S.positions()) {
			if(p.element().getKey().equals(key))
				return p.element().getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave nula");
		Entrada<K,V> nueva = new Entrada<K,V>(key,value);
		V toRet = null;
		try {
			if(S.isEmpty()) {
				S.addFirst(nueva);
				toRet = null;
			}
			else {
				Position<Entrada<K,V>> aux = S.first();
				boolean encontre = false;
				while(aux != null && !encontre) {
					if(aux.element().getKey().equals(key))
						encontre = true;
					else {
						if(aux == S.last())
							aux = null;
						else
							aux = S.next(aux);
					}
				}
				if(encontre) {
					toRet = aux.element().getValue();
					aux.element().setValue(value);
				}
				else {
					S.addLast(nueva);
					toRet = null;
				}
			}
		}
		catch(EmptyListException|InvalidPositionException|BoundaryViolationException E) {
			E.printStackTrace();
		}
		
		return toRet;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		V toRet = null;
		try {
			if(!S.isEmpty()) {
				Position<Entrada<K,V>> aux = S.first();
				boolean encontre = false;
				while(aux != null && !encontre) {
					if(aux.element().getKey().equals(key))
						encontre = true;
					else {
						if(aux == S.last())
							aux = null;
						else
							aux = S.next(aux);
					}
				}
				if(encontre) {
					toRet = aux.element().getValue();
					S.remove(aux);
				}
			}
		}
		catch(EmptyListException|InvalidPositionException|BoundaryViolationException E) {
			E.printStackTrace();
		}
		
		return toRet;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> toRet = new ListaSimplementeEnlazada<K>();
		for(Entrada<K,V> ent: S) {
			toRet.addLast(ent.getKey());
		}
		
		return toRet;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> toRet = new ListaSimplementeEnlazada<V>();
		for(Entrada<K,V> ent: S) {
			toRet.addLast(ent.getValue());
		}
		
		return toRet;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> toRet = new ListaSimplementeEnlazada<Entry<K,V>>();
		for(Position<Entrada<K,V>> ent: S.positions()) {
			toRet.addLast(ent.element());
		}
		return toRet;
	}
}

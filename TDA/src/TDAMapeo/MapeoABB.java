package TDAMapeo;

import Exceptions.InvalidKeyException;
import TDAColaCP.Comparador;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class MapeoABB<K extends Comparable<K>,V> implements Map<K,V> {
	
	private ArbolBinarioDeBusqueda<K,V> arbol;
	
	public MapeoABB() {
		arbol = new ArbolBinarioDeBusqueda<K,V>(new Comparador<K>());
	}

	@Override
	public int size() {
		return arbol.size();
	}

	@Override
	public boolean isEmpty() {
		return arbol.isEmpty();
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		V toRet = null;
		NodoABB<K,V> n = arbol.buscar(key);
		if(n!=null)
			toRet = n.getRotulo().getValue();
		return toRet;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null) throw new InvalidKeyException("Clave Inválida");
		V toRet = arbol.insertar(key, value);
		return toRet;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		V toRet = null;
		if(!isEmpty())
			toRet = arbol.eliminar(key);
		return toRet;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> toRet = new DoubleLinkedList<K>();
		for(Entry<K,V> e : arbol.entradas()) {
			toRet.addLast(e.getKey());
		}
		return toRet;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> toRet = new DoubleLinkedList<V>();
		for(Entry<K,V> e : arbol.entradas()) {
			toRet.addLast(e.getValue());
		}
		return toRet;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		return arbol.entradas();
	}

}

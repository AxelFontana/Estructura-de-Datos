package TDADiccionario;
import Exceptions.*;

import ImplementacionesListas.*;
import TDALista.*;
import java.util.Iterator;

public class DiccionarioConABB<K extends Comparable<K>, V> implements Dictionary<K,V> {

	protected ArbolBinarioDeBusqueda<K,V> arbol;
	
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
		
		public void setKey(Ke key) {
			clave = key;
		}
		
		public void setValor(Va valor) {
			value = valor;
		}
		
		public String toString() {
			return "("+clave+","+value+")";
		}
		
	}
	
	public DiccionarioConABB() {
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
	public Entry<K,V> find(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		Entry<K,V> toRet = null;
		NodoABB<K,V> nodo = arbol.buscar(key);
		if(nodo != null && !nodo.getEntradas().isEmpty()) {
			try {
				toRet = nodo.getEntradas().first().element();
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return toRet;
	}

	@Override
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave inválida");
		Iterable<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		if(arbol.pertenece(key))
			toRet = arbol.obtenerEntradas(key);
		return toRet;
	}

	@Override
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Clave Inválida");
		return arbol.insertar(key,value);
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e == null || e.getKey() == null) throw new InvalidEntryException("Entrada inválida");
		K aElim = e.getKey();
		Entry<K,V> toRet = null;
		if(!arbol.pertenece(aElim)) throw new InvalidEntryException("");
		V v = arbol.eliminar(aElim);
		toRet = new Entrada<K,V>(aElim,v);
		return toRet;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		for(K k: arbol.obtenerClaves()) {
			for(Entry<K,V> e : arbol.obtenerEntradas(k)) {
				toRet.addLast(e);
			}
		}
		return toRet;
	}
	
}
package TDADiccionario;

import java.util.Comparator;
import Exceptions.*;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class ArbolBinarioDeBusqueda<K extends Comparable<K>,V>{
	
	private int size;
	private NodoABB<K,V> raiz;
	private Comparator<K> comp;
	
	public ArbolBinarioDeBusqueda(Comparator<K> comp) {
		size = 0;
		raiz = new NodoABB<K,V>(null,null);
		this.comp = comp;
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
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean pertenece(K k) {
		return buscar(k).getClave() != null;
	}
	
	public NodoABB<K,V> buscar(K k){
		return buscarAux(k,raiz);
	}
	
	private NodoABB<K,V> buscarAux(K k, NodoABB<K,V> n){
		NodoABB<K,V> toRet = null;
		if(n.getClave() == null)
			toRet = n;
		else {
			int c = comp.compare(k, n.getClave());
			if(c == 0)
				toRet = n;
			else
				if(c<0)
					toRet = buscarAux(k, n.getHijoI());
				else
					toRet = buscarAux(k, n.getHijoD());
		}
		
		return toRet;
	}
	
	public Entry<K,V> insertar(K k, V v) {
		NodoABB<K,V> n = buscar(k);
		
		if(n.getClave() == null) {
			n.setClave(k);
			n.setHijoD(new NodoABB<K,V>(null,n));
			n.setHijoI(new NodoABB<K,V>(null,n));
		}
		Entry<K,V> toRet = new Entrada<K,V>(k,v);
		n.getEntradas().addLast(toRet);
		size++;
		return toRet;
		
	}
	
	public String toString() {
		return inOrden(raiz);
	}
	
	private String inOrden(NodoABB<K,V> n) {
		String toRet = "";
		if(n.getClave() != null) {
			toRet = "("+inOrden(n.getHijoI())+n.getClave()+inOrden(n.getHijoD())+")";
		}
		
		return toRet;
	}
	
	public V eliminar(K k) {
		Entry<K,V> toRet = null;
		NodoABB<K,V> n = buscar(k);
		if(n!=null) {
			try {
				if(!n.getEntradas().isEmpty()) {
					toRet = n.getEntradas().remove(n.getEntradas().first());
				}
				if(n.getEntradas().isEmpty()) {
					eliminarAux(n);
				}
			} catch (EmptyListException | InvalidPositionException e) {
				e.printStackTrace();
			}
			
		}

		size--;
		return toRet.getValue();
	}
	
	private boolean isExternal(NodoABB<K,V> n) {
		return n.getHijoD().getClave() == null && n.getHijoI().getClave() == null;
	}
	
	private boolean soloTieneHijoIzquierdo(NodoABB<K,V> n) {
		return n.getHijoI().getClave() != null && n.getHijoD().getClave() == null;
	}
	
	private boolean soloTieneHijoDerecho(NodoABB<K,V> n) {
		return n.getHijoD().getClave() != null && n.getHijoI().getClave() == null;
	}
	
	private void eliminarAux(NodoABB<K,V> n) {
		if(isExternal(n)) {
			if(n.getEntradas().isEmpty()) {
				n.setClave(null);
				n.setHijoD(null);
				n.setHijoI(null);
			}
		}
		else {
			if(soloTieneHijoIzquierdo(n)) {
				if(n!=raiz) {
					if(n.getPadre().getHijoI() == n)
						n.getPadre().setHijoI(n.getHijoI());
					else
						n.getPadre().setHijoD(n.getHijoI());
					n.getHijoI().setPadre(n.getPadre());
				}
				else {
					raiz = n.getHijoI();
				}
			}
			else {
				if(soloTieneHijoDerecho(n)) {
					if(n!=raiz) {
						if(n.getPadre().getHijoI() == n)
							n.getPadre().setHijoI(n.getHijoD());
						else
							n.getPadre().setHijoD(n.getHijoD());
						n.getHijoD().setPadre(n.getPadre());
					}
					else {
						raiz = n.getHijoD();
					}
				}
				else {
					n.setClave(eliminarMinimo(n.getHijoD()));
				}
			}
		}
	}
	
	private K eliminarMinimo(NodoABB<K,V> n) {
		K toRet = null;
		if(n.getHijoI().getClave() == null) {
			toRet = n.getClave();
			if(n.getHijoD().getClave() == null) {
				if(n.getEntradas().isEmpty()) {
					n.setClave(null);
					n.setHijoD(null);
					n.setHijoI(null);
				}
			}
			else {
				n.getPadre().setHijoD(n.getHijoD());
				n.getHijoD().setPadre(n.getPadre());
			}
		}
		
		else {
			toRet = eliminarMinimo(n.getHijoI());
		}
		
		return toRet;
	}

	public Iterable<Entry<K,V>> obtenerEntradas(K key) {
		NodoABB<K,V> n = buscar(key);
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		if(key != null) {
			toRet = n.getEntradas();
		}
		return toRet;
	}
	
	public Iterable<K> obtenerClaves() {
		PositionList<K> toRet = new DoubleLinkedList<K>();
		if(!isEmpty()) {
			obtenerClavesAux(raiz,toRet);
		}
		return toRet;
	}
	
	public void obtenerClavesAux(NodoABB<K,V> n,PositionList<K> lista) {
		lista.addLast(n.getClave());
		if(n.getHijoI().getClave() != null)
			obtenerClavesAux(n.getHijoI(),lista);
		if(n.getHijoD().getClave() != null)
			obtenerClavesAux(n.getHijoD(),lista);
	}
	
}

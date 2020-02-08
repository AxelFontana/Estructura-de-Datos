package TDAMapeo;

import java.util.Comparator;

import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class ArbolBinarioDeBusqueda<K extends Comparable<K>,V>{
	
	private int size;
	private NodoABB<K,V> raiz;
	private Comparator<K> comp;
	
	public ArbolBinarioDeBusqueda(Comparator<K> comp) {
		size = 0;
		raiz = new NodoABB<K,V>(null);
		this.comp = comp;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean pertenece(K k) {
		return buscar(k).getRotulo().getKey() != null;
	}
	
	public NodoABB<K,V> buscar(K k){
		return buscarAux(k,raiz);
	}
	
	private NodoABB<K,V> buscarAux(K k, NodoABB<K,V> n){
		NodoABB<K,V> toRet = null;
		if(n.getRotulo().getKey() == null)
			toRet = n;
		else {
			int c = comp.compare(k, n.getRotulo().getKey());
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
	
	public V insertar(K k, V v) {
		NodoABB<K,V> n = buscar(k);
		V toRet = null;
		if(n.getRotulo().getKey() == null) {
			n.setClave(k);
			n.setHijoD(new NodoABB<K,V>(n));
			n.setHijoI(new NodoABB<K,V>(n));
			size++;
		}
		toRet = n.getRotulo().getValue();
		n.setValor(v);
		return toRet;
		
	}
	
	public String toString() {
		return inOrden(raiz);
	}
	
	private String inOrden(NodoABB<K,V> n) {
		String toRet = "";
		if(n.getRotulo().getKey() != null) {
			toRet = "("+inOrden(n.getHijoI())+n.getRotulo().getKey()+inOrden(n.getHijoD())+")";
		}
		
		return toRet;
	}
	
	public V eliminar(K k) {
		V toRet = null;
		NodoABB<K,V> n = buscar(k);
		if(n!=null) {
			toRet = n.getRotulo().getValue();
			size--;
			eliminarAux(n);
		} 
			
		return toRet;
	}
	
	private boolean isExternal(NodoABB<K,V> n) {
		return n.getHijoD().getRotulo().getKey() == null && n.getHijoI().getRotulo().getKey() == null;
	}
	
	private boolean soloTieneHijoIzquierdo(NodoABB<K,V> n) {
		return n.getHijoI().getRotulo().getKey() != null && n.getHijoD().getRotulo().getKey() == null;
	}
	
	private boolean soloTieneHijoDerecho(NodoABB<K,V> n) {
		return n.getHijoD().getRotulo().getKey() != null && n.getHijoI().getRotulo().getKey() == null;
	}
	
	private void eliminarAux(NodoABB<K,V> n) {
		if(isExternal(n)) {
			n.setValor(null);
			n.setClave(null);
			n.setHijoD(null);
			n.setHijoI(null);
	
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
		if(n.getHijoI().getRotulo().getKey() == null) {
			toRet = n.getRotulo().getKey();
			if(n.getHijoD().getRotulo().getKey() == null) {
				n.setValor(null);
				n.setClave(null);
				n.setHijoD(null);
				n.setHijoI(null);
				
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
	
	public Iterable<Entry<K,V>> entradas(){
		PositionList<Entry<K,V>> toRet = new DoubleLinkedList<Entry<K,V>>();
		if(!isEmpty())
			entradasAux(toRet,raiz);
		return toRet;
	}
	
	private void entradasAux(PositionList<Entry<K,V>> lista, NodoABB<K,V> n){
		lista.addLast(n.getRotulo());
		if(n.getHijoI().getRotulo().getKey()!=null)
			entradasAux(lista,n.getHijoI());
		if(n.getHijoD().getRotulo().getKey()!=null)
			entradasAux(lista,n.getHijoD());
	}

}

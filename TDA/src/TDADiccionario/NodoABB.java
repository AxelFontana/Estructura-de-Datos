package TDADiccionario;

import TDALista.*;

public class NodoABB<K,V>{
	private K clave;
	private PositionList<Entry<K,V>> entradas;
	private NodoABB<K,V> padre, hijoD, hijoI;
	
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

	public NodoABB(K c, NodoABB<K,V> p) {
		clave = c;
		padre = p;
		entradas = new DoubleLinkedList<Entry<K,V>>();
		hijoD = null;
		hijoI = null;
	}

	public K getClave() {
		return clave;
	}
	
	public void setClave(K k) {
		clave = k;
	}

	public PositionList<Entry<K,V>> getEntradas() {
		return entradas;
	}

	public NodoABB<K,V> getPadre() {
		return padre;
	}

	public void setPadre(NodoABB<K,V> padre) {
		this.padre = padre;
	}

	public NodoABB<K, V> getHijoD() {
		return hijoD;
	}

	public void setHijoD(NodoABB<K,V> hijoD) {
		this.hijoD = hijoD;
	}

	public NodoABB<K, V> getHijoI() {
		return hijoI;
	}

	public void setHijoI(NodoABB<K, V> hijoI) {
		this.hijoI = hijoI;
	}

}

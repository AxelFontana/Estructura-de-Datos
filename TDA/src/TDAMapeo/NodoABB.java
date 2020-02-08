package TDAMapeo;

import TDALista.*;

public class NodoABB<K,V>{
	private Entrada<K,V> rotulo;
	private NodoABB<K,V> padre, hijoD, hijoI;
	
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
	

	public NodoABB(NodoABB<K,V> p) {
		rotulo = new Entrada<K,V>(null,null);
		padre = p;
		hijoD = null;
		hijoI = null;
	}

	public Entry<K,V> getRotulo() {
		return rotulo;
	}

	public void setClave(K clave) {
		rotulo.setKey(clave);
	}

	public void setValor(V valor) {
		rotulo.setValue(valor);
	}

	public NodoABB<K, V> getPadre() {
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

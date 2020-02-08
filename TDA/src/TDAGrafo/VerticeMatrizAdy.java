package TDAGrafo;

import TDALista.Position;
import TDAMapeo.OpenHMap;

public class VerticeMatrizAdy<V,E> extends OpenHMap<Object,Object> implements Vertex<V> {
	
	private V rotulo;
	private int indice;
	private Position<VerticeMatrizAdy<V,E>> posicionEnNodos;
	
	public VerticeMatrizAdy(V rotulo, int i) {
		this.rotulo = rotulo;
		indice = i;
	}

	public Position<VerticeMatrizAdy<V,E>> getPosicionEnNodos() {
		return posicionEnNodos;
	}

	public void setPosicionEnNodos(Position<VerticeMatrizAdy<V,E>> posicionEnNodos) {
		this.posicionEnNodos = posicionEnNodos;
	}

	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}

	public V element() {
		return rotulo;
	}
	
	public int getIndice() {
		return indice;
	}
}

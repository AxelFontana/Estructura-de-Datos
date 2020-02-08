package TDAGrafo;

import TDALista.*;
import TDAMapeo.*;
import ImplementacionesListas.*;

public class Vertice<V,E> extends OpenHMap<Object,Object> implements Vertex<V> {
	
	private V rotulo;
	private PositionList<Arco<V,E>> adyacentes;
	private Position<Vertice<V,E>> posicionEnNodos;
	
	public Vertice(V rotulo) {
		this.rotulo = rotulo;
		adyacentes = new DoubleLinkedList<Arco<V,E>>();
	}

	public PositionList<Arco<V,E>> getAdyacentes() {
		return adyacentes;
	}

	public void setAdyacentes(PositionList<Arco<V,E>> adyacentes) {
		this.adyacentes = adyacentes;
	}

	public Position<Vertice<V,E>> getPosicionEnNodos() {
		return posicionEnNodos;
	}

	public void setPosicionEnNodos(Position<Vertice<V,E>> posicionEnNodos) {
		this.posicionEnNodos = posicionEnNodos;
	}

	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}

	public V element() {
		return rotulo;
	}
}

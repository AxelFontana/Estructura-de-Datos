package TDAGrafo;

import TDALista.Position;
import TDAMapeo.OpenHMap;

public class VerticeListaArc<V,E> extends OpenHMap<Object,Object> implements Vertex<V>{
	
	private V rotulo;
	private Position<VerticeListaArc<V,E>> posicionEnNodos;
	
	public VerticeListaArc(V rotulo) {
		this.rotulo = rotulo;
	}
	
	public Position<VerticeListaArc<V,E>> getPosicionEnNodos() {
		return posicionEnNodos;
	}

	public void setPosicionEnNodos(Position<VerticeListaArc<V,E>> posicionEnNodos) {
		this.posicionEnNodos = posicionEnNodos;
	}

	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}

	@Override
	public V element() {
		return rotulo;
	}

}

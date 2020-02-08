package TDAGrafo;

import TDALista.Position;

public class ArcoListaArcos<V,E> implements Edge<E> {

	private E rotulo;
	private VerticeListaArc<V,E> predecesor, sucesor;
	private Position<ArcoListaArcos<V,E>> posicionEnAP;
	private Position<ArcoListaArcos<V,E>> posicionEnAS;
	private Position<ArcoListaArcos<V,E>> posicionListaArcos;
	
	public ArcoListaArcos(E e, VerticeListaArc<V,E> p, VerticeListaArc<V,E> s) {
		rotulo = e;
		sucesor = s;
		predecesor = p;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	public VerticeListaArc<V,E> getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(VerticeListaArc<V,E> precedecesor) {
		this.predecesor = precedecesor;
	}

	public VerticeListaArc<V,E> getSucesor() {
		return sucesor;
	}

	public void setSucesor(VerticeListaArc<V,E> sucesor) {
		this.sucesor = sucesor;
	}

	public Position<ArcoListaArcos<V,E>> getPosicionEnAP() {
		return posicionEnAP;
	}

	public void setPosicionEnAP(Position<ArcoListaArcos<V, E>> posicionEnAP) {
		this.posicionEnAP = posicionEnAP;
	}

	public Position<ArcoListaArcos<V, E>> getPosicionEnAS() {
		return posicionEnAS;
	}

	public void setPosicionEnAS(Position<ArcoListaArcos<V, E>> posicionEnAS) {
		this.posicionEnAS = posicionEnAS;
	}
	
	public Position<ArcoListaArcos<V,E>> getPosicionListaArcos(){
		return posicionListaArcos;
	}
	
	public void setPosicionListaArcos(Position<ArcoListaArcos<V,E>> p) {
		posicionListaArcos = p;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
}

package TDAGrafo;

import TDALista.Position;

public class ArcoMatrizAdy<V,E> implements Edge<E> {

	private E rotulo;
	private VerticeMatrizAdy<V,E> predecesor, sucesor;
	private Position<ArcoMatrizAdy<V,E>> posicionListaArcos;
	
	public ArcoMatrizAdy(E e, VerticeMatrizAdy<V,E> p, VerticeMatrizAdy<V,E> s) {
		rotulo = e;
		sucesor = s;
		predecesor = p;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	public VerticeMatrizAdy<V,E> getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(VerticeMatrizAdy<V,E> precedecesor) {
		this.predecesor = precedecesor;
	}

	public VerticeMatrizAdy<V,E> getSucesor() {
		return sucesor;
	}

	public void setSucesor(VerticeMatrizAdy<V,E> sucesor) {
		this.sucesor = sucesor;
	}
	
	public Position<ArcoMatrizAdy<V,E>> getPosicionListaArcos(){
		return posicionListaArcos;
	}
	
	public void setPosicionListaArcos(Position<ArcoMatrizAdy<V,E>> p) {
		posicionListaArcos = p;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
}


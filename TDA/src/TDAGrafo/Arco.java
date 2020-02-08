package TDAGrafo;

import TDALista.*;
public class Arco<V,E> implements Edge<E> {

	private E rotulo;
	private Vertice<V,E> predecesor, sucesor;
	private Position<Arco<V,E>> posicionEnAP;
	private Position<Arco<V,E>> posicionEnAS;
	private Position<Arco<V,E>> posicionListaArcos;
	
	public Arco(E e, Vertice<V,E> p, Vertice<V,E> s) {
		rotulo = e;
		sucesor = s;
		predecesor = p;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	public Vertice<V,E> getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(Vertice<V,E> precedecesor) {
		this.predecesor = precedecesor;
	}

	public Vertice<V,E> getSucesor() {
		return sucesor;
	}

	public void setSucesor(Vertice<V,E> sucesor) {
		this.sucesor = sucesor;
	}

	public Position<Arco<V,E>> getPosicionEnAP() {
		return posicionEnAP;
	}

	public void setPosicionEnAP(Position<Arco<V, E>> posicionEnAP) {
		this.posicionEnAP = posicionEnAP;
	}

	public Position<Arco<V, E>> getPosicionEnAS() {
		return posicionEnAS;
	}

	public void setPosicionEnAS(Position<Arco<V, E>> posicionEnAS) {
		this.posicionEnAS = posicionEnAS;
	}
	
	public Position<Arco<V,E>> getPosicionListaArcos(){
		return posicionListaArcos;
	}
	
	public void setPosicionListaArcos(Position<Arco<V,E>> p) {
		posicionListaArcos = p;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
	
	

}

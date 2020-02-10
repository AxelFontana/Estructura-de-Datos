
import TDALista.*;
public class Arco<V,E> implements Edge<E> {
	private E rotulo;
	private Vertices<V,E> sucesor, predecesor;
	private Position<Arco<V,E>> posicionEnAdyacentesP,posicionEnAdyacentesS;
	private Position<Arco<V,E>> posicionEnArco;
	public Arco( Vertices<V,E> predecesor, Vertices<V,E> sucesor,E rotulo ){
		this.rotulo=rotulo;
		this.predecesor=predecesor;
		this.sucesor=sucesor;
	}
	public E element() { 
		return rotulo; 
	}
	public Vertices<V,E> getPredecesor() {
		return predecesor;
	}
	public Vertices<V,E> getSucesor() {
		return sucesor;
	}
	public Position<Arco<V,E>> getPosicionEnAdyacentesP() {
		return posicionEnAdyacentesP;
	}
	public Position<Arco<V,E>> getPosicionEnAdyacentesS() {
		return posicionEnAdyacentesS;
	}
	public void setPosicionEnAdyacentesP(Position<Arco<V,E>> p) { 
		posicionEnAdyacentesP=p;
	}
	public void setPosicionEnAdyacentesS(Position<Arco<V,E>> p) { 
		posicionEnAdyacentesS=p;
	}
	public void setPosicionEnArco(Position<Arco<V,E>> p) {
		posicionEnArco=p;
	}
	public Position<Arco<V,E>> getPosicionEnArco(){
		return posicionEnArco;
	}
	public void setPredecesor(Vertices<V, E> predecesor) {
		this.predecesor = predecesor;
	}
	public void setSucesor(Vertices<V, E> sucesor) {
		this.sucesor = sucesor;
	}
	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
	

}

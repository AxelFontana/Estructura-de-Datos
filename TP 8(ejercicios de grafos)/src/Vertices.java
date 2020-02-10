
import TDALista.*;
public class Vertices<V,E> implements Vertex<V> {
	V rotulo;
	Position<Vertices<V,E>> PosicionEnNodos;
	PositionList<Arco<V,E>> adyacentes;
	
	
	public Vertices( V rotulo ) {
		this.rotulo = rotulo;
		adyacentes = new ListaDobleEnlaze<Arco<V,E>>();
	}
	// Setters y getters
	public V element() {
		return rotulo;
	}
	public void setRotulo(V nuevoRotulo) {
		rotulo=nuevoRotulo;
	}
	public PositionList<Arco<V,E>> getAdyacentes() { 
		return adyacentes;
	}
	public void setAdyacentes(PositionList<Arco<V,E>> a) {
		adyacentes=a;
	}
	public void setPosicionEnNodos(Position<Vertices<V,E>> p ) {
		PosicionEnNodos=p;
	}
	public Position<Vertices<V,E>> getPosicionEnNodos(){
		return PosicionEnNodos;
}
}
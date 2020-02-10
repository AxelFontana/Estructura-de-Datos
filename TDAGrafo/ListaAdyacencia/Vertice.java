package TDAGrafo.ListaAdyacencia;

import TDALista.*;

public class Vertice<V,E> implements Vertex<V> {

    private V rotulo;
    private Position<Vertice<V,E>> posicionEnNodos;
    private PositionList<Arco<V,E>> adyacentes;

    public Vertice(V rot){
        rotulo = rot;
        adyacentes = new ListaDobleEnlaze<Arco<V,E>>();
    }
    public V element() {
        return rotulo;
    }

    public void setRotulo(V nuevoRotulo) {
        rotulo = nuevoRotulo;
    }

    public PositionList<Arco<V, E>> getAdyacentes() {
        return adyacentes;
    }

    public void setAdyacentes(PositionList<Arco<V, E>> a) {
        adyacentes = a;
    }

    public void setPosicionEnNodos(Position<Vertice<V, E>> p) {
        posicionEnNodos = p;
    }

    public Position<Vertice<V, E>> getPosicionEnNodos() {
        return posicionEnNodos;
    }

}

package TDAGrafo.MatrizDeAdyacencia;

import TDAGrafo.ListaAdyacencia.Vertex;
import TDALista.Position;

public class VerticeM<V>implements Vertex<V> {
    private V rotulo;
    private int indice;
    private Position<VerticeM<V>> posicionEnNodos;

    public VerticeM(V rotulo, int i) {
        this.rotulo = rotulo;
        indice = i;
    }

    public Position<VerticeM<V>> getPosicionEnNodos() {
        return posicionEnNodos;
    }

    public void setPosicionEnNodos(Position<VerticeM<V>> posicionEnNodos) {
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

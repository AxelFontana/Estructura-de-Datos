package TDAGrafo.MatrizDeAdyacencia;

import TDAGrafo.ListaAdyacencia.Edge;
import TDALista.Position;

public class ArcoM<V,E>implements Edge<E> {
    private E rotulo;
    private VerticeM<V> predecesor, sucesor;
    private Position<ArcoM<V,E>> posicionListaArcos;

    public ArcoM(E e, VerticeM<V> p, VerticeM<V> s) {
        rotulo = e;
        sucesor = s;
        predecesor = p;
    }

    @Override
    public E element() {
        return rotulo;
    }

    public VerticeM<V> getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(VerticeM<V> precedecesor) {
        this.predecesor = precedecesor;
    }

    public VerticeM<V> getSucesor() {
        return sucesor;
    }

    public void setSucesor(VerticeM<V> sucesor) {
        this.sucesor = sucesor;
    }

    public Position<ArcoM<V,E>> getPosicionListaArcos(){
        return posicionListaArcos;
    }

    public void setPosicionListaArcos(Position<ArcoM<V,E>> p) {
        posicionListaArcos = p;
    }

    public void setRotulo(E rotulo) {
        this.rotulo = rotulo;
    }
}

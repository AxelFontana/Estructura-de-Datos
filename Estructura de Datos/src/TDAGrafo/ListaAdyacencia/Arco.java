package TDAGrafo.ListaAdyacencia;

import TDALista.*;

public class Arco<V,E> implements Edge<E> {

    private E rot;
    private Vertice<V,E> predecesor,sucesor;
    private Position<Arco<V,E>> posicionEnArco;
    private Position<Arco<V,E>> posicionEnAdyacentesP,posicionEnAdyacentesS;

    public Arco(Vertice<V,E> predecesor, Vertice<V,E> sucesor, E rotulo){
        rot = rotulo;
        this.predecesor = predecesor;
        this.sucesor = sucesor;
    }

    public E element() {
        return rot;
    }
    public Vertice<V,E> getPredecesor() {
        return predecesor;
    }
    public Vertice<V,E> getSucesor() {
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
    public Position<Arco<V, E>> getPosicionEnArco() {
        return posicionEnArco;
    }
    public void setPosicionEnArco(Position<Arco<V,E>> p) {
        posicionEnArco=p;
    }


    public void setPredecesor(Vertice<V, E> predecesor) {
        this.predecesor = predecesor;
    }
    public void setSucesor(Vertice<V, E> sucesor) {
        this.sucesor = sucesor;
    }
    public void setRotulo(E rotulo) {
        rot = rotulo;
    }
}

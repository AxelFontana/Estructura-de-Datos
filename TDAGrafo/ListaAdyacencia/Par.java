package TDAGrafo.ListaAdyacencia;
import TDALista.*;
public class Par {
    PositionList<Vertex<Character>> Camino;
    int peso;
    public Par() {
        Camino=new ListaDobleEnlaze<Vertex<Character>>();
        peso=999999;
    }
    public int getPeso() {
        return peso;
    }
    public void setPeso(int p) {
        peso=p;
    }
    public PositionList<Vertex<Character>> getCamino(){
        return Camino;
    }
    public void setCamino(PositionList<Vertex<Character>>ca) {
        Camino=ca;
    }

}

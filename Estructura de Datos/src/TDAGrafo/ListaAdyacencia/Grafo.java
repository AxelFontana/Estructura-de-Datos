package TDAGrafo.ListaAdyacencia;

import Exceptions.*;
import TDALista.*;

public class Grafo<V,E> implements Graph<V,E> {

    PositionList<Vertice<V,E>> nodos;
    PositionList<Arco<V,E>> arcos;

    public Grafo(){
        nodos = new ListaDobleEnlaze<Vertice<V,E>>();
        arcos = new ListaDobleEnlaze<Arco<V,E>>();
    }

    private Vertice<V,E> checkVertex (Vertex<V> v) throws InvalidVertexException {
        if(nodos.isEmpty()){
            throw new InvalidVertexException("Vertice Invalido");
        }
        if(v == null){
            throw  new InvalidVertexException("Vertice Invalido");
        }
        Vertice<V, E> ret;
        try {
            ret = (Vertice<V ,E>) v;
        }
        catch (ClassCastException e){
            throw new InvalidVertexException("Vertice invalido");
        }
        return ret;
    }
    private Arco<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
        if (arcos.isEmpty()) {
            throw new InvalidEdgeException("aca");
        }
        if (v == null)
            throw new InvalidEdgeException("ArcoD invalido");
        Arco<V, E> ret;
        try {
            ret = (Arco<V, E>) v;
        } catch (ClassCastException e) {
            throw new InvalidEdgeException("O aca");
        }
        return ret;
    }
    /**
     * Devuelve una colección iterable de vértices.
     * @return Una colección iterable de vértices.
     */
    public Iterable<Vertex<V>> vertices(){
        PositionList<Vertex<V>>toRet = new ListaDobleEnlaze<Vertex<V>>();
        for(Vertice<V,E> v : nodos){
            toRet.addLast(v);
        }
        return toRet;
    }

    /**
     * Devuelve una colección iterable de arcos.
     * @return Una colección iterable de arcos.
     */
    public Iterable<Edge<E>> edges(){
        PositionList<Edge<E>> toRet = new ListaDobleEnlaze<Edge<E>>();
        for (Edge<E> e : arcos){
            toRet.addLast(e);
        }
        return toRet;
    }

    /**
     * Devuelve una colección iterable de arcos incidentes a un vértice v.
     * @param v Un vértice.
     * @return Una colección iterable de arcos incidentes a un vértice v.
     * @throws InvalidVertexException si el vértice es inválido.
     */
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException{
        PositionList<Edge<E>> listaRet = new ListaDobleEnlaze<Edge<E>>();
        Vertice<V, E> vert = checkVertex(v);
        for(Arco<V, E> a : vert.getAdyacentes()){
            listaRet.addLast(a);
        }
        return listaRet;
    }


    /**
     * Devuelve el vértice opuesto a un Arco E y un vértice V.
     * @param v Un vértice
     * @param e Un arco
     * @return El vértice opuesto a un Arco E y un vértice V.
     * @throws InvalidVertexException si el vértice es inválido.
     * @throws InvalidEdgeException si el arco es inválido.
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException{
        Vertice<V, E> vertice = checkVertex(v);
        Arco<V, E> arc = checkEdge(e);
        Vertex<V> toRet;

        if(arc.getPredecesor() == vertice){
            toRet = arc.getSucesor();
        }
        else{
            if(arc.getSucesor() == vertice){
                toRet = arc.getPredecesor();
            }
            else{
                throw new InvalidEdgeException("No coinciden arco y vertice");
            }
        }
        return toRet;
    }

    /**
     * Devuelve un Arreglo de 2 elementos con lo vértices extremos de un Arco e.
     * @param  e Un arco
     * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
     * @throws InvalidEdgeException si el arco es inválido.
     */
    public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException{
        Arco<V, E> arc = checkEdge(e);
        Vertex<V> ret [] = (Vertex<V>[]) new Vertex[2];
        ret[0] = arc.getPredecesor();
        ret[1]= arc.getSucesor();
        return ret;
    }

    /**
     * Devuelve verdadero si el vértice w es adyacente al vértice v.
     * @param v Un vértice
     * @param w Un vértice
     * @return Verdadero si el vértice w es adyacente al vértice v, falso en caso contrario.
     * @throws InvalidVertexException si uno de los vértices es inválido.
     */
    public boolean areAdjacent(Vertex<V> v,Vertex<V> w) throws InvalidVertexException{

        Vertice<V, E> vertice = checkVertex(v);
        Vertice<V, E> vertice2 = checkVertex(w);
        boolean son = false;

        try{
            Position<Arco<V, E>> act = !vertice.getAdyacentes().isEmpty() ? vertice.getAdyacentes().first() :null;
            while(act != null && !son){
                if((act.element().getPredecesor() == vertice && act.element().getSucesor() == vertice2)
                || act.element().getPredecesor() == vertice2 && act.element().getSucesor() == vertice ){
                    son = true;
                }
                else{
                    if (act == vertice.getAdyacentes().last()){
                        act = null;
                    }
                    else{
                        act = vertice.getAdyacentes().next(act);
                    }
                }
            }

        } catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
            e.printStackTrace();
        }
    return son;
    }

    /**
     * Reemplaza el rótulo de v por un rótulo x.
     * @param v Un vértice
     * @param x Rótulo
     * @return El rótulo anterior del vértice v al reemplazarlo por un rótulo x.
     * @throws InvalidVertexException si el vértice es inválido.
     */
    public V replace(Vertex<V> v, V x) throws InvalidVertexException{
        Vertice<V, E> vert = checkVertex(v);
        V ret = vert.element();
        vert.setRotulo(x);
        return ret;
    }

    /**
     * Inserta un nuevo vértice con rótulo x.
     * @param x rótulo del nuevo vértice
     * @return Un nuevo vértice insertado.
     */
    public Vertex<V> insertVertex(V x){
        Vertice<V, E> nuevo = new Vertice<V, E>(x);
        nodos.addLast(nuevo);

        try{
            nuevo.setPosicionEnNodos(nodos.last());
        }
        catch (EmptyListException e){
            e.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Inserta un nuevo arco con rótulo e, con vértices extremos v y w.
     * @param v Un vértice
     * @param w Un vértice
     * @param e rótulo del nuevo arco.
     * @return Un nuevo arco.
     * @throws InvalidVertexException si uno de los vértices es inválido.
     */
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException{
        Vertice<V, E> vert1 = checkVertex(v);
        Vertice<V, E> vert2 = checkVertex(w);
        Arco<V, E> nuevo = new Arco<V, E>(vert1, vert2, e);
        try {

            arcos.addLast(nuevo);
            nuevo.setPosicionEnArco(arcos.last());
            vert1.getAdyacentes().addLast(nuevo);
            vert2.getAdyacentes().addLast(nuevo);
            nuevo.setPosicionEnAdyacentesP(vert1.getAdyacentes().last());
            nuevo.setPosicionEnAdyacentesS(vert2.getAdyacentes().last());

        }
        catch (EmptyListException e1){
            e1.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Remueve un vértice V y retorna su rótulo.
     * @param v Un vértice
     * @return rótulo de V.
     * @throws InvalidVertexException si el vértice es inválido.
     */
    public V removeVertex(Vertex<V> v) throws InvalidVertexException{
        Vertice<V, E> vert = checkVertex(v);
        V ret = vert.element();
        try{
            nodos.remove(vert.getPosicionEnNodos());
            for(Arco<V, E> arc : vert.getAdyacentes()){
                removeEdge(arc);
            }
            vert.setAdyacentes(null);
            vert.setRotulo(null);
            vert.setPosicionEnNodos(null);

        }
        catch (InvalidPositionException | InvalidEdgeException e){
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Remueve un arco e y retorna su rótulo.
     * @param e Un arco
     * @return rótulo de E.
     * @throws InvalidEdgeException si el arco es inválido.
     */
    public E removeEdge(Edge<E> e) throws InvalidEdgeException{
        Arco<V ,E> arc = checkEdge(e);
        E ret = arc.element();
        try{

            arcos.remove(arc.getPosicionEnArco());
            arc.getPredecesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesP());
            arc.getSucesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesS());
            arc.setPosicionEnAdyacentesP(null);
            arc.setPosicionEnAdyacentesS(null);
            arc.setRotulo(null);
            arc.setSucesor(null);
            arc.setPredecesor(null);
            arc.setPosicionEnArco(null);
        }
        catch (InvalidPositionException E1){
            E1.printStackTrace();
        }
    return ret;
    }
}

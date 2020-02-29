package TDAGrafo.MatrizDeAdyacencia;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDAGrafo.ListaAdyacencia.*;
import TDALista.*;
public class MatrizM<V, E> implements Graph<V, E> {

    protected PositionList<VerticeM<V>> vertices;
    protected PositionList<ArcoM<V, E>> arcos;
    protected Edge<E>[][] matriz;
    protected int cantVertices;

    public MatrizM(int n) {
        vertices = new ListaDobleEnlaze<VerticeM<V>>();
        arcos = new ListaDobleEnlaze<ArcoM<V, E>>();
        matriz = (Edge<E>[][]) new ArcoM[n][n];
        cantVertices = 0;
    }

    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> lista = new ListaDobleEnlaze<Vertex<V>>();
        for (Vertex<V> v : vertices) {
            lista.addLast(v);
        }

        return lista;
    }

    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> lista = new ListaDobleEnlaze<Edge<E>>();
        for (Edge<E> e : arcos) {
            lista.addLast(e);
        }

        return lista;
    }

    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {

        if (v == null)
            throw new InvalidVertexException("Vertice invalido");
        VerticeM<V> vv = (VerticeM<V>) v;
        int fila = vv.getIndice();
        PositionList<Edge<E>> lista = new ListaDobleEnlaze<Edge<E>>();
        for (int col = 0; col < cantVertices; col++)
            if (matriz[fila][col] != null)
                lista.addLast(matriz[fila][col]);

        return lista;
    }

    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {

        if (v == null)
            throw new InvalidVertexException("Vertice invalido");
        VerticeM<V> vv = (VerticeM<V>) v;
        if (e == null)
            throw new InvalidEdgeException("Arco invalido");
        ArcoM<V, E> ee = (ArcoM<V, E>) e;
        Vertex<V> salida = null;
        if (ee.getPredecesor() == vv)
            salida = ee.getSucesor();
        else {
            if (ee.getSucesor() == vv)
                salida = ee.getPredecesor();
            else
                throw new InvalidEdgeException("Ninguno de los extremos coincide con el vertice");
        }
        return salida;
    }

    public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
        if (e == null)
            throw new InvalidEdgeException("Arco invalido");
        Vertex<V>[] salida = (VerticeM<V>[]) new VerticeM[2];
        ArcoM<V, E> ee = (ArcoM<V, E>) e;
        salida[0] = ee.getPredecesor();
        salida[1] = ee.getSucesor();

        return salida;
    }

    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {

        if (v == null || w == null)
            throw new InvalidVertexException("Vertice invalido");
        VerticeM<V> vv = (VerticeM<V>) v;
        VerticeM<V> ww = (VerticeM<V>) w;
        int i = vv.getIndice();
        int j = ww.getIndice();

        return matriz[i][j] != null;
    }

    public V replace(Vertex<V> v, V x) throws InvalidVertexException {
        if (v == null)
            throw new InvalidVertexException("Vertice invalido");

        VerticeM<V> vv = (VerticeM<V>) v;
        V removed = v.element();
        vv.setRotulo(x);

        return removed;
    }

    public Vertex<V> insertVertex(V x) {
        VerticeM<V> vv = new VerticeM<V>(x, cantVertices);
        cantVertices++;
        vertices.addLast(vv);
        try {
            vv.setPosicionEnNodos(vertices.last());

        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        return vv;
    }

    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {

        if (v == null || w == null)
            throw new InvalidVertexException("Vertice invalido");
        VerticeM<V> vv = (VerticeM<V>) v;
        VerticeM<V> ww = (VerticeM<V>) w;
        int fila = vv.getIndice();
        int col = ww.getIndice();
        ArcoM<V, E> arco = new ArcoM<V, E>(e, vv, ww);
        matriz[fila][col] = matriz[col][fila] = arco;
        arcos.addLast(arco);
        try {
            arco.setPosicionListaArcos(arcos.last());
        } catch (EmptyListException exc) {
            System.out.println(exc.getMessage());
        }

        return arco;
    }

    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        if (v == null)
            throw new InvalidVertexException("Vertice invalido");
        VerticeM<V> vv = (VerticeM<V>) v;
        V removed = v.element();
        int fila = vv.getIndice();
        try {
            for (int col = 0; col < cantVertices; col++) {
                if (matriz[fila][col] != null) {
                    ArcoM<V, E> arc = (ArcoM<V, E>) matriz[fila][col];
                    removeEdge(matriz[fila][col]);
                }
            }

            vertices.remove(vv.getPosicionEnNodos());
            cantVertices--;

        } catch (InvalidPositionException | InvalidEdgeException e) {
            System.out.println(e.getMessage());
        }

        return removed;
    }

    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        if (e == null)
            throw new InvalidEdgeException("Arco invalido");

        ArcoM<V, E> ee = (ArcoM<V, E>) e;
        E removed = e.element();
        int fila = ee.getPredecesor().getIndice();
        int col = ee.getSucesor().getIndice();
        matriz[fila][col] = matriz[col][fila] = null;
        try {
            arcos.remove(ee.getPosicionListaArcos());
            ee.setRotulo(null);
            ee.setPosicionListaArcos(null);
            ee.setPredecesor(null);
            ee.setSucesor(null);
        } catch (InvalidPositionException exc) {
            System.out.println(exc.getMessage());
        }

        return removed;
    }
}

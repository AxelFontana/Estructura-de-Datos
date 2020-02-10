package TDAGrafo.ListaAdyacencia;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDALista.*;

public class GrafoD<V, E> implements GraphD<V, E> {
    private PositionList<Vertice<V, E>> nodos;
    private PositionList<Arco<V, E>> arcos;

    public GrafoD() {
        nodos = new ListaDobleEnlaze<Vertice<V, E>>();
        arcos = new ListaDobleEnlaze<Arco<V, E>>();
    }

    /**
     * Devuelve una colecci�n iterable de v�rtices.
     *
     * @return Una colecci�n iterable de v�rtices.
     */
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> verts = new ListaDobleEnlaze<Vertex<V>>();
        for (Vertex<V> v : nodos) {
            verts.addLast(v);
        }
        return verts;
    }

    /**
     * Devuelve una colecci�n iterable de arcos.
     *
     * @return Una colecci�n iterable de arcos.
     */
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> arcs = new ListaDobleEnlaze<Edge<E>>();
        for (Edge<E> e : arcos) {
            arcs.addLast(e);
        }
        return arcs;
    }

    /**
     * Devuelve una colecci�n iterable de arcos incidentes a un v�rtice v.
     *
     * @param v Un v�rtice.
     * @return Una colecci�n iterable de arcos incidentes a un v�rtice v.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        Vertice<V, E> vert = checkVertex(v);
        PositionList<Edge<E>> l = new ListaDobleEnlaze<Edge<E>>();
        try {
            for (Arco<V, E> a : arcos) {
                if (endvertices(a)[1] == vert) {
                    l.addLast(a);
                }
            }
        } catch (InvalidEdgeException e) {
            e.printStackTrace();
        }
        return l;

    }

    /**
     * Devuelve una colecci�n iterable de arcos adyacentes a un v�rtice v.
     *
     * @param v Un v�rtice
     * @return Una colecci�n iterable de arcos adyacentes a un v�rtice v.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
        Vertice<V, E> vert = checkVertex(v);
        PositionList<Edge<E>> l = new ListaDobleEnlaze<Edge<E>>();
        for (Arco<V, E> a : vert.getAdyacentes()) {
            l.addLast(a);
        }
        return l;
    }

    /**
     * Devuelve el v�rtice opuesto a un Arco E y un v�rtice V.
     *
     * @param v Un v�rtice
     * @param e Un arco
     * @return El v�rtice opuesto a un Arco E y un v�rtice V.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     * @throws InvalidEdgeException   si el arco es inv�lido.
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
        Vertice<V, E> vert = checkVertex(v);
        Arco<V, E> arc = checkEdge(e);
        if (arc.getPredecesor() == vert) {
            return arc.getSucesor();
        } else {
            if (arc.getSucesor() == vert) {
                return arc.getPredecesor();
            } else {
                throw new InvalidEdgeException("arco y vertice no coinciden");
            }
        }
    }

    /**
     * Devuelve un Arreglo de 2 elementos con lo v�rtices extremos de un Arco e.
     *
     * @param e Un arco
     * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
     * @throws InvalidEdgeException si el arco es inv�lido.
     */
    public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
        Vertice<V, E>[] Vertice = (Vertice<V, E>[]) new Vertice[2];
        Arco<V, E> arc = checkEdge(e);
        Vertice[0] = arc.getPredecesor();
        Vertice[1] = arc.getSucesor();
        return Vertice;
    }

    /**
     * Devuelve verdadero si el v�rtice w es adyacente al v�rtice v.
     *
     * @param v Un v�rtice
     * @param w Un v�rtice
     * @return Verdadero si el v�rtice w es adyacente al v�rtice v, falso en caso
     *         contrario.
     * @throws InvalidVertexException si uno de los v�rtices es inv�lido.
     */
    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
        Vertice<V, E> vert = checkVertex(v);
        Vertice<V, E> vert2 = checkVertex(w);
        boolean es = false;
        for (Arco<V, E> a : vert.getAdyacentes()) {
            try {
                if (this.opposite(vert, a) == vert2) {
                    es = true;
                }
            } catch (InvalidEdgeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        }
        return es;
    }

    /**
     * Reemplaza el r�tulo de v por un r�tulo x.
     *
     * @param v Un v�rtice
     * @param x R�tulo
     * @return El r�tulo anterior del v�rtice v al reemplazarlo por un r�tulo x.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    public V replace(Vertex<V> v, V x) throws InvalidVertexException {
        Vertice<V, E> vert = checkVertex(v);
        V ret = vert.element();
        vert.setRotulo(x);
        return ret;
    }

    /**
     * Inserta un nuevo v�rtice con r�tulo x.
     *
     * @param x r�tulo del nuevo v�rtice
     * @return Un nuevo v�rtice insertado.
     */
    public Vertex<V> insertVertex(V x) {
        Vertice<V, E> nuevo = new Vertice<V, E>(x);
        nodos.addLast(nuevo);
        try {
            nuevo.setPosicionEnNodos(nodos.last());
        } catch (EmptyListException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Inserta un nuevo arco con r�tulo e, desde un v�rtice v a un v�rtice w.
     *
     * @param v Un v�rtice
     * @param w Un v�rtice
     * @param e r�tulo del nuevo arco.
     * @return Un nuevo arco insertado desde un v�rtice V a un v�rtice W.
     * @throws InvalidVertexException si uno de los v�rtices es inv�lido.
     */
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
        Vertice<V, E> vert = checkVertex(v);
        Vertice<V, E> vert2 = checkVertex(w);
        Arco<V, E> nuevo = new Arco<V, E>(vert, vert2, e);
        arcos.addLast(nuevo);
        try {
            nuevo.setPosicionEnArco(arcos.last());
            nuevo.setPredecesor(vert);
            nuevo.setSucesor(vert2);
            vert.getAdyacentes().addLast(nuevo);
            nuevo.setPosicionEnAdyacentesP(vert.getAdyacentes().last());
        } catch (EmptyListException ee) {
            throw new InvalidVertexException("");
        }
        return nuevo;
    }

    /**
     * Remueve un v�rtice V y retorna su r�tulo.
     *
     * @param v Un v�rtice
     * @return r�tulo de V.
     * @throws InvalidVertexException si el v�rtice es inv�lido.
     */
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        Vertice<V, E> vertice = checkVertex(v);
        V ret = vertice.element();
        try {
            nodos.remove(vertice.getPosicionEnNodos());

            for (Arco<V, E> arc : arcos) {
                if (arc.getPredecesor() == vertice || arc.getSucesor() == vertice) {
                    this.removeEdge(arc);
                }
            }

            vertice.setAdyacentes(null);
            vertice.setPosicionEnNodos(null);
            vertice.setRotulo(null);
        } catch (InvalidPositionException | InvalidEdgeException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Remueve un arco e y retorna su r�tulo.
     *
     * @param e Un arco
     * @return r�tulo de E.
     * @throws InvalidEdgeException si el arco es inv�lido.
     */
    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        Arco<V, E> arc = checkEdge(e);
        E ret = arc.element();
        try {
            arcos.remove(arc.getPosicionEnArco());
            arc.getPredecesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesP());
            arc.setPosicionEnAdyacentesP(null);
            arc.setPosicionEnArco(null);
            arc.setPredecesor(null);
            arc.setSucesor(null);
            arc.setRotulo(null);
        } catch (InvalidPositionException ee) {
            ee.printStackTrace();
        }
        return ret;

    }

    private Vertice<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
        if (nodos.isEmpty()) {
            throw new InvalidVertexException("vertice invalido");
        }
        if (v == null)
            throw new InvalidVertexException("VerticeD invalido");
        Vertice<V, E> ret;
        try {
            ret = (Vertice<V, E>) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("invalido");
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

}

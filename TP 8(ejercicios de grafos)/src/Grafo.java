
import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;

import TDALista.*;

public class Grafo<V, E> implements Graph<V, E> {
	PositionList<Vertices<V, E>> nodos;
	PositionList<Arco<V, E>> arcos;

	public Grafo() {
		nodos = new ListaDobleEnlaze<Vertices<V, E>>();
		arcos = new ListaDobleEnlaze<Arco<V, E>>();
	}

	/**
	 * Devuelve una colección iterable de vértices.
	 * 
	 * @return Una colección iterable de vértices.
	 */
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new ListaDobleEnlaze<Vertex<V>>();
		for (Vertex<V> v : nodos) {
			lista.addLast(v);
		}
		return lista;
	}

	/**
	 * Devuelve una colección iterable de arcos.
	 * 
	 * @return Una colección iterable de arcos.
	 */
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new ListaDobleEnlaze<Edge<E>>();
		for (Edge<E> e : arcos) {
			lista.addLast(e);

		}
		return lista;
	}

	/**
	 * Devuelve una colección iterable de arcos incidentes a un vértice v.
	 * 
	 * @param v Un vértice.
	 * @return Una colección iterable de arcos incidentes a un vértice v.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertices<V, E> vert = checkVertex(v);
		PositionList<Edge<E>> l = new ListaDobleEnlaze<Edge<E>>();

		for (Arco<V, E> a : vert.getAdyacentes()) {
			l.addLast(a);
		}

		return l;

	}

	/**
	 * Devuelve el vértice opuesto a un Arco E y un vértice V.
	 * 
	 * @param v Un vértice
	 * @param e Un arco
	 * @return El vértice opuesto a un Arco E y un vértice V.
	 * @throws InvalidVertexException si el vértice es inválido.
	 * @throws InvalidEdgeException   si el arco es inválido.
	 */

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertices<V, E> vertice = checkVertex(v);
		Arco<V, E> arc = checkEdge(e);
		Vertex<V> ret;
		if (arc.getPredecesor() == vertice) {
			ret = arc.getSucesor();
		} else {
			if (arc.getSucesor() == vertice) {
				ret = arc.getPredecesor();
			} else {
				throw new InvalidEdgeException("no coinciden arco y vertice");
			}
		}
		return ret;
	}

	/**
	 * Devuelve un Arreglo de 2 elementos con lo vértices extremos de un Arco e.
	 * 
	 * @param e Un arco
	 * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Vertex<V>[] a = (Vertex<V>[]) new Vertex[2];
		Arco<V, E> arc = checkEdge(e);
		a[0] = arc.getPredecesor();
		a[1] = arc.getSucesor();
		return a;
	}

	/**
	 * Devuelve verdadero si el vértice w es adyacente al vértice v.
	 * 
	 * @param v Un vértice
	 * @param w Un vértice
	 * @return Verdadero si el vértice w es adyacente al vértice v, falso en caso
	 *         contrario.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertices<V, E> vertice = checkVertex(v);
		Vertices<V, E> vertice2 = checkVertex(w);
		boolean son = false;
		for (Arco<V, E> a : vertice.getAdyacentes()) {
			if ((a.getPredecesor() == vertice && a.getSucesor() == vertice2)
					|| (a.getPredecesor() == vertice2 && a.getSucesor() == vertice)) {
				son = true;
				break;
			}
		}
		return son;
	}

	/**
	 * Reemplaza el rótulo de v por un rótulo x.
	 * 
	 * @param v Un vértice
	 * @param x Rótulo
	 * @return El rótulo anterior del vértice v al reemplazarlo por un rótulo x.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertices<V, E> vertice = this.checkVertex(v);
		V ret = vertice.element();
		vertice.setRotulo(x);
		return ret;
	}

	/**
	 * Inserta un nuevo vértice con rótulo x.
	 * 
	 * @param x rótulo del nuevo vértice
	 * @return Un nuevo vértice insertado.
	 */
	public Vertex<V> insertVertex(V x) {
		Vertices<V, E> nuevo = new Vertices<V, E>(x);
		nodos.addLast(nuevo);
		try {
			nuevo.setPosicionEnNodos(nodos.last());
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return nuevo;
	}

	/**
	 * Inserta un nuevo arco con rótulo e, con vértices extremos v y w.
	 * 
	 * @param v Un vértice
	 * @param w Un vértice
	 * @param e rótulo del nuevo arco.
	 * @return Un nuevo arco.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertices<V, E> vertice = this.checkVertex(v);
		Vertices<V, E> vertice2 = this.checkVertex(w);
		Arco<V, E> nuevo = new Arco<V, E>(vertice, vertice2, e);
		try {
			arcos.addLast(nuevo);
			vertice.getAdyacentes().addLast(nuevo);
			vertice2.getAdyacentes().addLast(nuevo);
			nuevo.setPosicionEnAdyacentesP(vertice.getAdyacentes().last());
			nuevo.setPosicionEnAdyacentesS(vertice2.getAdyacentes().last());
			nuevo.setPosicionEnArco(arcos.last());
		} catch (EmptyListException ee) {
			ee.printStackTrace();
		}
		return nuevo;
	}

	/**
	 * Remueve un vértice V y retorna su rótulo.
	 * 
	 * @param v Un vértice
	 * @return rótulo de V.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertices<V, E> vertice = this.checkVertex(v);
		V ret = vertice.element();
		try {
			nodos.remove(vertice.getPosicionEnNodos());
			for (Arco<V, E> a : vertice.getAdyacentes()) {
				this.removeEdge(a);
			}
			vertice.setAdyacentes(null);
			vertice.setRotulo(null);
			vertice.setPosicionEnNodos(null);
		} catch (InvalidPositionException | InvalidEdgeException e) {

		}
		return ret;
	}

	/**
	 * Remueve un arco e y retorna su rótulo.
	 * 
	 * @param e Un arco
	 * @return rótulo de E.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {

		Arco<V, E> arc = checkEdge(e);
		E ret = e.element();
		try {
			arcos.remove(arc.getPosicionEnArco());
			arc.getPredecesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesP());
			arc.getSucesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesS());
			arc.setPosicionEnAdyacentesP(null);
			arc.setPosicionEnAdyacentesS(null);
			arc.setPosicionEnArco(null);
			arc.setSucesor(null);
			arc.setPredecesor(null);
			arc.setRotulo(null);
		} catch (InvalidPositionException ee) {
			throw new InvalidEdgeException("");

		}
		return ret;
	}

	private Vertices<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (nodos.isEmpty()) {
			throw new InvalidVertexException("vertice invalido");
		}
		if (v == null)
			throw new InvalidVertexException("VerticeD invalido");
		Vertices<V, E> ret;
		try {
			ret = (Vertices<V, E>) v;
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
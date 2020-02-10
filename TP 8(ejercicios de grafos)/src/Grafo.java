
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
	 * Devuelve una colecci�n iterable de v�rtices.
	 * 
	 * @return Una colecci�n iterable de v�rtices.
	 */
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new ListaDobleEnlaze<Vertex<V>>();
		for (Vertex<V> v : nodos) {
			lista.addLast(v);
		}
		return lista;
	}

	/**
	 * Devuelve una colecci�n iterable de arcos.
	 * 
	 * @return Una colecci�n iterable de arcos.
	 */
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new ListaDobleEnlaze<Edge<E>>();
		for (Edge<E> e : arcos) {
			lista.addLast(e);

		}
		return lista;
	}

	/**
	 * Devuelve una colecci�n iterable de arcos incidentes a un v�rtice v.
	 * 
	 * @param v Un v�rtice.
	 * @return Una colecci�n iterable de arcos incidentes a un v�rtice v.
	 * @throws InvalidVertexException si el v�rtice es inv�lido.
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
	 * Devuelve el v�rtice opuesto a un Arco E y un v�rtice V.
	 * 
	 * @param v Un v�rtice
	 * @param e Un arco
	 * @return El v�rtice opuesto a un Arco E y un v�rtice V.
	 * @throws InvalidVertexException si el v�rtice es inv�lido.
	 * @throws InvalidEdgeException   si el arco es inv�lido.
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
	 * Devuelve un Arreglo de 2 elementos con lo v�rtices extremos de un Arco e.
	 * 
	 * @param e Un arco
	 * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
	 * @throws InvalidEdgeException si el arco es inv�lido.
	 */
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Vertex<V>[] a = (Vertex<V>[]) new Vertex[2];
		Arco<V, E> arc = checkEdge(e);
		a[0] = arc.getPredecesor();
		a[1] = arc.getSucesor();
		return a;
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
	 * Reemplaza el r�tulo de v por un r�tulo x.
	 * 
	 * @param v Un v�rtice
	 * @param x R�tulo
	 * @return El r�tulo anterior del v�rtice v al reemplazarlo por un r�tulo x.
	 * @throws InvalidVertexException si el v�rtice es inv�lido.
	 */
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertices<V, E> vertice = this.checkVertex(v);
		V ret = vertice.element();
		vertice.setRotulo(x);
		return ret;
	}

	/**
	 * Inserta un nuevo v�rtice con r�tulo x.
	 * 
	 * @param x r�tulo del nuevo v�rtice
	 * @return Un nuevo v�rtice insertado.
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
	 * Inserta un nuevo arco con r�tulo e, con v�rtices extremos v y w.
	 * 
	 * @param v Un v�rtice
	 * @param w Un v�rtice
	 * @param e r�tulo del nuevo arco.
	 * @return Un nuevo arco.
	 * @throws InvalidVertexException si uno de los v�rtices es inv�lido.
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
	 * Remueve un v�rtice V y retorna su r�tulo.
	 * 
	 * @param v Un v�rtice
	 * @return r�tulo de V.
	 * @throws InvalidVertexException si el v�rtice es inv�lido.
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
	 * Remueve un arco e y retorna su r�tulo.
	 * 
	 * @param e Un arco
	 * @return r�tulo de E.
	 * @throws InvalidEdgeException si el arco es inv�lido.
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
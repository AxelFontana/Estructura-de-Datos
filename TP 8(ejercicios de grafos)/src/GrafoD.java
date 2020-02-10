

import Excepciones.*;
import TDALista.*;


public class GrafoD<V,E> implements GraphD<V,E> {
	private PositionList<Vertices<V,E>> nodos;
	private PositionList<Arco<V,E>> arcos;
	public GrafoD() {
		nodos = new ListaDobleEnlaze<Vertices<V,E>>();
		arcos=new ListaDobleEnlaze<Arco<V,E>>();
		}

	/**
	 * Devuelve una colección iterable de vértices.
	 * @return Una colección iterable de vértices.
	 */
	public Iterable<Vertex<V>> vertices(){
		PositionList<Vertex<V>> verts=new ListaDobleEnlaze<Vertex<V>>();
		for(Vertex<V> v:nodos) {
			verts.addLast(v);
		}
		return verts;
	}
	
	/**
	 * Devuelve una colección iterable de arcos.
	 * @return Una colección iterable de arcos.
	 */
	public Iterable<Edge<E>> edges(){
		PositionList<Edge<E>> arcs=new ListaDobleEnlaze<Edge<E>>();
		for(Edge<E> e:arcos) {
			arcs.addLast(e);
		}
		return arcs;
	}
	
	/**
	 * Devuelve una colección iterable de arcos incidentes a un vértice v.
	 * @param v Un vértice.
	 * @return Una colección iterable de arcos incidentes a un vértice v.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException{
		Vertices<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> l = new ListaDobleEnlaze<Edge<E>>();
		try {
			for(Arco<V,E> a : arcos) {
				if(endvertices(a)[1] == vert) {
					l.addLast(a);
				}
			}
		} catch (InvalidEdgeException e) {
			e.printStackTrace();
		}
		return l;
		
	}
	
	/**
	 * Devuelve una colección iterable de arcos adyacentes a un vértice v.
	 * @param v Un vértice
	 * @return Una colección iterable de arcos adyacentes a un vértice v.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException{
		Vertices<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> l = new ListaDobleEnlaze<Edge<E>>();
		for(Arco<V,E> a : vert.getAdyacentes()) {
			l.addLast(a);
		}
		return l;
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
		Vertices<V,E> vert=checkVertex(v);
		Arco<V,E> arc=checkEdge(e);
		if(arc.getPredecesor()==vert) {
			return arc.getSucesor();
		}
		else {
			if(arc.getSucesor()==vert) {
				return arc.getPredecesor();
			}
			else {
				throw new InvalidEdgeException("arco y vertice no coinciden");
			}
		}
	}
	
	/**
	 * Devuelve un Arreglo de 2 elementos con lo vértices extremos de un Arco e.
	 * @param  e Un arco
	 * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
	public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException{
		Vertices<V,E>[] vertices=(Vertices<V,E>[])new Vertices[2];
		Arco<V,E> arc=checkEdge(e);
		vertices[0]=arc.getPredecesor();
		vertices[1]=arc.getSucesor();
		return vertices;
	}
	
	/**
	 * Devuelve verdadero si el vértice w es adyacente al vértice v.
	 * @param v Un vértice
	 * @param w Un vértice
	 * @return Verdadero si el vértice w es adyacente al vértice v, falso en caso contrario.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
	public boolean areAdjacent(Vertex<V> v,Vertex<V> w) throws InvalidVertexException{
		Vertices<V,E> vert=checkVertex(v);
		Vertices<V,E> vert2=checkVertex(w);
		boolean es=false;
		for(Arco<V,E> a:vert.getAdyacentes()) {
			try {
				if(this.opposite(vert, a)==vert2) {
					es=true;
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
	 * Reemplaza el rótulo de v por un rótulo x.
	 * @param v Un vértice
	 * @param x Rótulo
	 * @return El rótulo anterior del vértice v al reemplazarlo por un rótulo x.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public V replace(Vertex<V> v, V x) throws InvalidVertexException{
		Vertices<V,E> vert=checkVertex(v);
		V ret=vert.element();
		vert.setRotulo(x);
		return ret;
	}
	
	/**
	 * Inserta un nuevo vértice con rótulo x.
	 * @param x rótulo del nuevo vértice
	 * @return Un nuevo vértice insertado.
	 */
	public Vertex<V> insertVertex(V x){
		Vertices<V,E> nuevo=new Vertices<V,E>(x);
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
	 * Inserta un nuevo arco con rótulo e, desde un vértice v a un vértice w.
	 * @param v Un vértice
	 * @param w Un vértice
	 * @param e rótulo del nuevo arco.
	 * @return Un nuevo arco insertado desde un vértice V a un vértice W.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException{
		Vertices<V,E> vert=checkVertex(v);
		Vertices<V,E> vert2=checkVertex(w);
		Arco<V,E> nuevo=new Arco<V,E>(vert,vert2,e);
		arcos.addLast(nuevo);
		try {
			nuevo.setPosicionEnArco(arcos.last());
			nuevo.setPredecesor(vert);
			nuevo.setSucesor(vert2);
			vert.getAdyacentes().addLast(nuevo);
			nuevo.setPosicionEnAdyacentesP(vert.getAdyacentes().last());
		}
		catch(EmptyListException ee) {
			throw new InvalidVertexException("");
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
		Vertices<V,E> vertice=checkVertex(v);
		V ret=vertice.element();
		try {
		nodos.remove(vertice.getPosicionEnNodos());
	
		for(Arco<V,E> arc:arcos) {
			if(arc.getPredecesor()==vertice || arc.getSucesor()==vertice) {
				this.removeEdge(arc);
			}
		}
		
		vertice.setAdyacentes(null);
		vertice.setPosicionEnNodos(null);
		vertice.setRotulo(null);
		}
		catch(InvalidPositionException | InvalidEdgeException e) {
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
		Arco<V,E> arc=checkEdge(e);
		E ret=arc.element();
		try {
			arcos.remove(arc.getPosicionEnArco());
			arc.getPredecesor().getAdyacentes().remove(arc.getPosicionEnAdyacentesP());
			arc.setPosicionEnAdyacentesP(null);
			arc.setPosicionEnArco(null);
			arc.setPredecesor(null);
			arc.setSucesor(null);
			arc.setRotulo(null);
		}
		catch(InvalidPositionException ee) {
			ee.printStackTrace();
		}
		return ret;
		
	}
	private Vertices<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (nodos.isEmpty()){
			throw new InvalidVertexException("vertice invalido");
		}
		if (v==null)
			throw new InvalidVertexException("VerticeD invalido");
		Vertices<V,E> ret;
		try {
			ret = (Vertices<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidVertexException("invalido");
		}
		return ret;
	}
	
	private Arco<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
		if (arcos.isEmpty()){
			throw new InvalidEdgeException("aca");
		}
		if (v==null)
			throw new InvalidEdgeException("ArcoD invalido");
		Arco<V,E> ret;
		try {
			ret = (Arco<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidEdgeException("O aca");
		}
		return ret;
	}

}
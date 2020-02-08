package TDAGrafo;

import Exceptions.*;
import java.util.Iterator;
import TDALista.*;

public class GrafoListaAdy<V,E> implements Graph<V,E> {

	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public GrafoListaAdy() {
		nodos = new DoubleLinkedList<Vertice<V,E>>();
		arcos = new DoubleLinkedList<Arco<V,E>>();
	}
	
	private Vertice<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (nodos.isEmpty()){
			throw new InvalidVertexException("Vertice invalido");
		}
		if (v==null)
			throw new InvalidVertexException("Vertice invalido");
		Vertice<V,E> ret;
		try {
			ret = (Vertice<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidVertexException("Vertice invalido");
		}
		return ret;
	}
	
	private Arco<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
		if (arcos.isEmpty()){
			throw new InvalidEdgeException("Arco invalido");
		}
		if (v==null)
			throw new InvalidEdgeException("Arco invalido");
		Arco<V,E> ret;
		try {
			ret = (Arco<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidEdgeException("Arco invalido");
		}
		return ret;
	}
	
	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> toRet = new DoubleLinkedList<Vertex<V>>();
		for(Vertex<V> v : nodos) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> v : arcos) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		Vertice<V,E> vv = checkVertex(v);
		for(Arco<V,E> ar : vv.getAdyacentes())
			toRet.addLast(ar);
		return toRet;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Arco<V,E> arc = checkEdge(e);
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> toRet = null;
			if(arc.getPredecesor() == vv)
				toRet = arc.getSucesor();
			else
				if(arc.getSucesor() == vv)
					toRet = arc.getPredecesor();
				else
					throw new InvalidEdgeException("Arco inválido");
		return toRet;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdge(e);
		Vertex<V>[] toRet = (Vertex<V>[]) new Vertex[2]; 
		toRet[0] = arc.getPredecesor();
		toRet[1] = arc.getSucesor();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean toRet = false;
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		Iterator<Arco<V,E>> it1 = vv.getAdyacentes().iterator();
		Arco<V,E> a1;
		try {
			while(!toRet && it1.hasNext()) {
				a1 = it1.next();
				if(opposite(vv,a1) == ww)
					toRet = true;
			}
		}
		catch(InvalidEdgeException e) {
			e.printStackTrace();
		}
		return toRet;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V,E> vert = checkVertex(v);
		V toRet = vert.element();
		vert.setRotulo(x);
		
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertice<V,E> nuevo = new Vertice<V,E>(x);
		nodos.addLast(nuevo);
		try {
		nuevo.setPosicionEnNodos(nodos.last());
		}
		catch(EmptyListException e) {
			e.printStackTrace();
		}
		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		Arco<V,E> nuevo = new Arco<V,E>(e,vv,ww);
		arcos.addLast(nuevo);
		try {
		vv.getAdyacentes().addLast(nuevo);
		ww.getAdyacentes().addLast(nuevo);
		nuevo.setPosicionEnAP(vv.getAdyacentes().last());
		nuevo.setPosicionEnAS(ww.getAdyacentes().last());
		nuevo.setPosicionListaArcos(arcos.last());
		}
		catch(EmptyListException er) {
			er.printStackTrace();
		}
		return nuevo;
		
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		try {
			for(Arco<V,E> a : vv.getAdyacentes()) {
				removeEdge(a);
			}
			nodos.remove(vv.getPosicionEnNodos());
			
		}
		catch(InvalidEdgeException | InvalidPositionException e) {
			e.printStackTrace();
		}
		
		return toRet;
		
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		if(arcos.isEmpty()) throw new InvalidEdgeException("No hay arcos");
		Arco<V,E> arc = checkEdge(e);
		Vertice<V,E> v1 = arc.getPredecesor();
		Vertice<V,E> v2 = arc.getSucesor();
		E toRet = null;
		Position<Arco<V,E>> pAux = null;
		try {
			v1.getAdyacentes().remove(arc.getPosicionEnAP());
			v2.getAdyacentes().remove(arc.getPosicionEnAS());
			pAux = arc.getPosicionListaArcos();
			toRet = pAux.element().element();
			arcos.remove(pAux);
			
		}
		catch(InvalidPositionException er) {
			er.printStackTrace();
		}
		
		if(pAux == null) throw new InvalidEdgeException("El arco no se encuentra en la lista de arcos");
		
		return toRet;
	}

}

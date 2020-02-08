package TDAGrafo;

import java.util.Iterator;

import Exceptions.*;
import TDALista.*;

public class GrafoListaArcos<V,E> implements Graph<V,E>{
	
	PositionList<VerticeListaArc<V,E>> nodos;
	PositionList<ArcoListaArcos<V,E>> arcos;
	
	public GrafoListaArcos() {
		nodos = new DoubleLinkedList<VerticeListaArc<V,E>>();
		arcos = new DoubleLinkedList<ArcoListaArcos<V,E>>();
	}
	
	private VerticeListaArc<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (nodos.isEmpty()){
			throw new InvalidVertexException("Vertice invalido");
		}
		if (v==null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeListaArc<V,E> ret;
		try {
			ret = (VerticeListaArc<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidVertexException("Vertice invalido");
		}
		return ret;
	}
	
	private ArcoListaArcos<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
		if (arcos.isEmpty()){
			throw new InvalidEdgeException("Arco invalido");
		}
		if (v==null)
			throw new InvalidEdgeException("Arco invalido");
		ArcoListaArcos<V,E> ret;
		try {
			ret = (ArcoListaArcos<V,E>) v;
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
		for(ArcoListaArcos<V,E> v : arcos) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		VerticeListaArc<V,E> vv = checkVertex(v);
		for(ArcoListaArcos<V,E> ar : arcos) {
			if(ar.getPredecesor() == vv || ar.getSucesor() == vv)
				toRet.addLast(ar);
		}
		return toRet;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		ArcoListaArcos<V,E> arc = checkEdge(e);
		VerticeListaArc<V,E> vv = checkVertex(v);
		VerticeListaArc<V,E> toRet = null;
		if(arc.getPredecesor() == vv)
			toRet = arc.getSucesor();
		else {
			if(arc.getSucesor() == vv)
				toRet = arc.getPredecesor();
			else throw new InvalidEdgeException("Arco Inválido");
		}
		
		return toRet;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoListaArcos<V,E> arc = checkEdge(e);
		Vertex<V>[] toRet = (Vertex<V>[]) new Vertex[2]; 
		toRet[0] = arc.getPredecesor();
		toRet[1] = arc.getSucesor();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean toRet = false;
		VerticeListaArc<V,E> vv = checkVertex(v);
		VerticeListaArc<V,E> ww = checkVertex(w);
		Iterator<ArcoListaArcos<V,E>> it = arcos.iterator();
		ArcoListaArcos<V,E> arc = null;
		while(it.hasNext() && !toRet) {
			arc = it.next();
			if((arc.getPredecesor() == vv && arc.getSucesor() == ww) || (arc.getPredecesor() == ww && arc.getSucesor() == vv))
				toRet = true;
		}
		return toRet;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeListaArc<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		vv.setRotulo(x);
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeListaArc<V,E> nuevo = new VerticeListaArc<V,E>(x);
		nodos.addLast(nuevo);
		try {
			nuevo.setPosicionEnNodos(nodos.last());
		} catch (EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeListaArc<V,E> vv =checkVertex(v);
		VerticeListaArc<V,E> ww = checkVertex(w);
		ArcoListaArcos<V,E> nuevo = new ArcoListaArcos<V,E>(e,vv,ww);
		arcos.addLast(nuevo);
		try {
			nuevo.setPosicionListaArcos(arcos.last());
		} catch (EmptyListException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return nuevo;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeListaArc<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		try {
			for(ArcoListaArcos<V,E> arc : arcos) {
				if(arc.getPredecesor() == vv || arc.getSucesor() == vv)
					arcos.remove(arc.getPosicionListaArcos());
			}
			
			nodos.remove(vv.getPosicionEnNodos());
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toRet;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoListaArcos<V,E> arc = checkEdge(e);
		E toRet = arc.element();
		try {
			arcos.remove(arc.getPosicionListaArcos());
		} catch (InvalidPositionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return toRet;
	}


}

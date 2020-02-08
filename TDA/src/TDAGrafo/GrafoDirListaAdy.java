package TDAGrafo;

import java.util.Iterator;
import TDACola.*;
import Exceptions.*;
import TDALista.*;

public class GrafoDirListaAdy<V,E> implements GraphD<V,E> {
	
	protected PositionList<Vertice<V,E>> vertices;
	protected PositionList<Arco<V,E>> arcos;
	private final Object Estado;
	private final Object Visitado;
	private final Object NoVisitado;
	
	public GrafoDirListaAdy() {
		vertices = new DoubleLinkedList<Vertice<V,E>>();
		arcos = new DoubleLinkedList<Arco<V,E>>();
		Estado = new Object();
		Visitado = new Object();
		NoVisitado = new Object();
	}
	
	private Vertice<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (vertices.isEmpty()){
			throw new InvalidVertexException("");
		}
		if (v==null)
			throw new InvalidVertexException("Vertice invalido");
		Vertice<V,E> ret;
		try {
			ret = (Vertice<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidVertexException("");
		}
		return ret;
	}
	
	private Arco<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
		if (arcos.isEmpty()){
			throw new InvalidEdgeException("aca");
		}
		if (v==null)
			throw new InvalidEdgeException("Arco invalido");
		Arco<V,E> ret;
		try {
			ret = (Arco<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidEdgeException("O aca");
		}
		return ret;
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> toRet = new DoubleLinkedList<Vertex<V>>();
		for(Vertice<V,E> v: vertices) {
			toRet.addLast(v);
		}
		
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> v: arcos) {
			toRet.addLast(v);
		}
		
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> ar : arcos) {
			if(ar.getSucesor() == vv) {
				toRet.addLast(ar);
			}
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> ar : vv.getAdyacentes()) {
			toRet.addLast(ar);
		}
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
		Arco<V,E> ar = checkEdge(e);
		Vertex<V> [] toRet = (Vertex<V> []) new Vertex[2];
		toRet[0] = ar.getPredecesor();
		toRet[1] = ar.getSucesor();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean toRet = false;
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		Iterator<Arco<V,E>> it1 = vv.getAdyacentes().iterator();
		Arco<V,E> a1;
		while(!toRet && it1.hasNext()) {
			a1 = it1.next();
			if(a1.getSucesor() == ww) {
				toRet = true;
			}
		}
		
		if(!toRet) {
			Iterator<Arco<V,E>> it2 = ww.getAdyacentes().iterator();
			Arco<V,E> a2;
			while(it2.hasNext() && !toRet) {
				a2 = it2.next();
				if(a2.getSucesor() == vv)
					toRet = true;
			}
		}
		
		return toRet;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		vv.setRotulo(x);
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertice<V,E> nuevo = new Vertice<V,E>(x);
		vertices.addLast(nuevo);
		
		try {
			nuevo.put(Estado, NoVisitado);
			nuevo.setPosicionEnNodos(vertices.last());
		} catch (EmptyListException | InvalidKeyException e) {
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
			nuevo.setPosicionEnAP(vv.getAdyacentes().last());
			nuevo.setPosicionListaArcos(arcos.last());
			nuevo.setPredecesor(vv);
			nuevo.setSucesor(ww);
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
		Position<Vertice<V,E>> pos = vv.getPosicionEnNodos();
		try {
			vertices.remove(pos);
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		return toRet;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdge(e);
		E toRet = null;
		Vertice<V,E> v1 = arc.getPredecesor();
		Position<Arco<V,E>> pos1 = arc.getPosicionEnAP();
		Position<Arco<V,E>> aux = arc.getPosicionListaArcos();
		toRet = aux.element().element();
		try {
			arcos.remove(aux);
			v1.getAdyacentes().remove(pos1);
		}
		catch(InvalidPositionException er) {
			er.printStackTrace();
		}
		
		return toRet;
	}
	
	public boolean dfs(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean es = false;
		Vertice<V,E> vv = checkVertex(v);	
		Vertice<V,E> ww = checkVertex(w);
		try {
		vv.put(Estado, Visitado);
			if(vv == ww)
				es = true;
			else
				for(Arco<V,E> arc : vv.getAdyacentes()) {
						Vertice<V,E> suc = arc.getSucesor();
						if(suc.get(Estado) == NoVisitado)
							es = es || dfs(suc,w);
					}
		}
		catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		return es;
	}
	
	public boolean bfs(Vertex<V> v, Vertex<V> w) throws InvalidVertexException{
		boolean toRet = false;
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		Queue<Vertice<V,E>> cola = new ArrayQueue<Vertice<V,E>>();
		cola.enqueue(vv);
		try {
			while(!cola.isEmpty()) {
				Vertice<V,E> aux = cola.dequeue();
				aux.put(Estado, Visitado);
				if(aux == ww)
					toRet = true;
				else {
					for(Arco<V,E> arc : vv.getAdyacentes()) {
						if(arc.getSucesor().get(Estado) == NoVisitado)
							cola.enqueue(arc.getSucesor());
					}
				}
			}
		}
		catch(EmptyQueueException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return toRet;
	}

}

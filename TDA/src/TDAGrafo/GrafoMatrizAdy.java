package TDAGrafo;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDALista.*;

public class GrafoMatrizAdy<V,E> implements Graph<V,E> {
	
	protected PositionList<VerticeMatrizAdy<V,E>> vertices;
	protected PositionList<ArcoMatrizAdy<V,E>> arcos;
	protected Edge<E>[][] matriz;
	protected int cantVert;
	
	public GrafoMatrizAdy(int n) {
		vertices = new DoubleLinkedList<VerticeMatrizAdy<V,E>>();
		arcos = new DoubleLinkedList<ArcoMatrizAdy<V,E>>();
		matriz = (Edge<E>[][]) new ArcoMatrizAdy[n][n];
		cantVert = 0;
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<n; j++) {
				matriz[i][j] = null;
			}
		}
	}
	
	private VerticeMatrizAdy<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if (vertices.isEmpty()){
			throw new InvalidVertexException("Vertice invalido");
		}
		if (v==null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeMatrizAdy<V,E> ret;
		try {
			ret = (VerticeMatrizAdy<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidVertexException("Vertice invalido");
		}
		return ret;
	}
	
	private ArcoMatrizAdy<V, E> checkEdge(Edge<E> v) throws InvalidEdgeException {
		if (arcos.isEmpty()){
			throw new InvalidEdgeException("Arco invalido");
		}
		if (v==null)
			throw new InvalidEdgeException("Arco invalido");
		ArcoMatrizAdy<V,E> ret;
		try {
			ret = (ArcoMatrizAdy<V,E>) v;
		} catch (ClassCastException e) {
			throw new InvalidEdgeException("Arco invalido");
		}
		return ret;
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> toRet = new DoubleLinkedList<Vertex<V>>();
		for(Vertex<V> v : vertices) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		for(Edge<E> v : arcos) {
			toRet.addLast(v);
		}
		return toRet;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		PositionList<Edge<E>> toRet = new DoubleLinkedList<Edge<E>>();
		int fila = vv.getIndice();
		for(int col = 0; col<cantVert; col++) {
			if(matriz[fila][col] != null)
				toRet.addLast(matriz[fila][col]);
		}
		return toRet;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		ArcoMatrizAdy<V,E> ee = checkEdge(e);
		if(ee.getPredecesor() == vv)
			return ee.getSucesor();
		else
			if(ee.getSucesor() == vv)
				return ee.getPredecesor();
			else throw new InvalidEdgeException("Vertice y Arco no relacionados");
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoMatrizAdy<V,E> ee = checkEdge(e);
		Vertex<V>[] toRet = (Vertex<V> []) new VerticeMatrizAdy[2];
		toRet[0] = ee.getPredecesor();
		toRet[1] = ee.getSucesor();
		return toRet;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		VerticeMatrizAdy<V,E> ww = checkVertex(w);
		int i = vv.getIndice();
		int j = ww.getIndice();
		return matriz[i][j] != null;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		vv.setRotulo(x);
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeMatrizAdy<V,E> nuevo = new VerticeMatrizAdy<V,E>(x,cantVert);
		cantVert++;
		vertices.addLast(nuevo);
		try {
			nuevo.setPosicionEnNodos(vertices.last());
		} catch (EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		VerticeMatrizAdy<V,E> ww = checkVertex(w);
		ArcoMatrizAdy<V,E> nuevo = new ArcoMatrizAdy<V,E>(e,vv,ww);
		int fila = vv.getIndice();
		int col = ww.getIndice();
		arcos.addLast(nuevo);
		try {
			nuevo.setPosicionListaArcos(arcos.last());
		} catch (EmptyListException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		matriz[fila][col] = nuevo;
		matriz[col][fila] = nuevo;
		
		return nuevo;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeMatrizAdy<V,E> vv = checkVertex(v);
		V toRet = vv.element();
		int i = vv.getIndice();
		for(int j = 0; j<cantVert; j++) {
			try {
				ArcoMatrizAdy<V,E> elim = checkEdge(matriz[i][j]);
				arcos.remove(elim.getPosicionListaArcos());
			} catch (InvalidEdgeException | InvalidPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			matriz[i][j] = null;
		}
		try {
			vertices.remove(vv.getPosicionEnNodos());
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cantVert--;
		return toRet;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoMatrizAdy<V,E> ee = checkEdge(e);
		E toRet = ee.element();
		VerticeMatrizAdy<V,E> v = ee.getPredecesor();
		VerticeMatrizAdy<V,E> w = ee.getSucesor();
		matriz[v.getIndice()][w.getIndice()] = null;
		matriz[w.getIndice()][v.getIndice()] = null;
		try {
			arcos.remove(ee.getPosicionListaArcos());
		} catch (InvalidPositionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return toRet;
	}

}

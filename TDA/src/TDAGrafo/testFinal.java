package TDAGrafo;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDALista.*;
import TDAMapeo.*;

public class testFinal<V,E> {
	private static final Object Estado = new Object();
	private static final Object NoVisitado = new Object();
	private static final Object Visitado = new Object();
	
	public Map<Vertex<V>,Pair<PositionList<Vertex<V>>,Float>> metodo(GraphD<V,Float> G, Vertex<V> v){
		Map<Vertex<V>,Pair<PositionList<Vertex<V>>,Float>> mapeo = new OpenHMap<Vertex<V>,Pair<PositionList<Vertex<V>>,Float>>();
		for(Vertex<V> vv : G.vertices()) {
			try {
				vv.put(Estado, NoVisitado);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(Vertex<V> w : G.vertices()) {
				Pair<PositionList<Vertex<V>>,Float> par = new Pair<PositionList<Vertex<V>>,Float>(new DoubleLinkedList<Vertex<V>>(),(float) 0);
				try {
					if(w.get(Estado) == NoVisitado) {
						metodoAux(G,v,w,par);
						par.getFirst().addLast(w);
						mapeo.put(w, par);
					}
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return mapeo;
	}

	private boolean metodoAux(GraphD<V, Float> G, Vertex<V> origen, Vertex<V> destino, Pair<PositionList<Vertex<V>>, Float> par) {
		par.getFirst().addLast(origen);
		float costo = 0; boolean encontre = false;
		try {
			origen.put(Estado, Visitado);
			if(origen == destino) {
				for(Vertex<V> v : G.vertices()) {
					v.put(Estado, NoVisitado);
				}
				return true;
			}
			if(origen != destino) {
				for(Edge<Float> e : G.succesorEdges(origen)) {
					if(G.opposite(origen, e).get(Estado) == NoVisitado)
						costo+=e.element();
						par.setSecond(costo);
						encontre = metodoAux(G,G.opposite(origen, e),destino,par);
						if(encontre) {
							for(Vertex<V> v : G.vertices()) {
								v.put(Estado, NoVisitado);
							}
							return true;
						}
				}
			}
			
			for(Vertex<V> v : G.vertices()) {
				v.put(Estado, NoVisitado);
			}
			par.getFirst().remove(par.getFirst().last());
			
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

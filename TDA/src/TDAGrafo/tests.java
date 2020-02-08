package TDAGrafo;


import Exceptions.*;
import TDACola.ArrayQueue;
import TDACola.Queue;

public class tests<V,E> {
	
	private static final Object Estado = new Object();
	private static final Object NoVisitado = new Object();
	private static final Object Visitado = new Object();
	
	public static <V,E> boolean dfs2(GraphD<V,E> G, Vertex<V> v, Vertex<V> w) {
		boolean toRet = false;
		try {
			v.put(Estado, Visitado);
			if(v == w)
				toRet = true;
			else {
				for(Edge<E> arc : G.succesorEdges(v)) {
					if(G.opposite(v, arc).get(Estado) == NoVisitado)
						toRet = toRet || dfs2(G,G.opposite(v, arc), w);
				}
			}
		}
		catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return toRet;
	}
	
	public static <V,E> boolean bfs(GraphD<V,E> G, Vertex<V> v, Vertex<V> w) {
		boolean toRet = false;
		Queue<Vertex<V>> cola = new ArrayQueue<Vertex<V>>();
		cola.enqueue(v);
		try {
			v.put(Estado, Visitado);
			while(!cola.isEmpty()) {
				Vertex<V> aux = cola.dequeue();
				if(aux == w)
					toRet = true;
				for(Edge<E> arc : G.succesorEdges(v)) {
					if(G.opposite(v, arc).get(Estado) == NoVisitado) {
						cola.enqueue(G.opposite(v, arc));
						G.opposite(v, arc).put(Estado, Visitado);
					}
				}
			}
		}
		catch(InvalidVertexException | InvalidEdgeException | EmptyQueueException | InvalidKeyException er) {
			er.printStackTrace();
		}
		return toRet;
	}
	
}

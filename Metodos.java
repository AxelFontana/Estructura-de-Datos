package MetodosParcial;
import GrafoDListaAdyacente.*;
import listaDoble.*;
import TDAMapa.*;

public class Metodos {
	
	/**
	 * Tener que utilizar el metodo afuera de la estructura causa que por cada
	 * Vertice pasado por parametro V en la cascara, tenga que recorrer todo el grafo
	 * para analizar cuales son adyacentes a ese V, cuando si estuviera en la
	 * estructura podría acceder a la lista de adyacencia y listo.
	 * Recorre todos los vertices del grafo de manera ordenada
	 * @param g grafo del que voy a analizar todos los vertices.
	 */
	public void DFS(Graph<String,Integer>g) {
		Map<Vertex<String>,Boolean> mapa = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : g.vertices()) {
				mapa.put(v,false);
			}
			for(Vertex<String> v : g.vertices()) {
				if(mapa.get(v) == false)
					DFS(g,v,mapa);
			}
		}
		catch(InvalidKeyException e) {}
	}
	private void DFS(Graph<String,Integer>g, Vertex<String> v,Map<Vertex<String>,Boolean>m) {
		System.out.println(v.element().toString());
		try {
			m.put(v,true);
			//para cada vertice adyacente a v uso DFS
			for(Vertex<String> w : g.vertices()) {
			/*
			 * Si estuviera adentro del grafo en ves de hacerg.areAdjacent
			 * Hago un for(Vertex<String> w : v.getAdyacentes()) y listo.
			 */
				if(g.areAdjacent(v,w)) {
					if(m.get(w) == false)
						DFS(g,w,m);
				}
			}
		}
		catch(InvalidVertexException|InvalidKeyException e) {}
	}

	/**
	 * Mucho más simple si se implentara adentro de la clase grafo por lo mismo
	 * que el DFS.
	 */
	public void BFS(Graph<String,Integer> g) {
		Map<Vertex<String>,Boolean> mapa = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : g.vertices()) {
				mapa.put(v,false);	
			}
			
			for(Vertex<String> v : g.vertices()) {
				if(mapa.get(v) == false) {
					BFS(g,v,mapa);
				}
			}
		}
		catch(InvalidKeyException e) {}
	}
	private void BFS(Graph<String,Integer> g, Vertex<String> p, Map<Vertex<String>,Boolean>m) {
		PositionList<Vertex<String>> cola = new DoubleLinkedList<Vertex<String>>();
		cola.addLast(p);
		try {
			while(!cola.isEmpty()) {
				Vertex<String> w = cola.remove(cola.first());
				System.out.println(w.element().toString());
				m.put(w,true);
				for(Vertex<String> x : g.vertices()) {
					if(g.areAdjacent(w,x)) {
						if(m.get(x) == false)
							cola.addLast(x);
					}
				}
			}
		}
		catch(InvalidVertexException|InvalidKeyException|InvalidPositionException|EmptyListException e) {}
	}
	
	/**
	DFS con backtracking, marca y desmarca: Permite
	encontrar un camino de costo mínimo entre dos vertices g y f
	 */
	public PositionList<Vertex<String>> caminoEconomico(Graph<String,Integer>g, Vertex<String>d,Vertex<String>f) {
		Map<Vertex<String>,Boolean> mapeo = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : g.vertices())
				mapeo.put(v,false);
			PositionList<Vertex<String>> caminoActual = new DoubleLinkedList<Vertex<String>>();
			PositionList<Vertex<String>> caminoMinimo = new DoubleLinkedList<Vertex<String>>();
			PositionList<Integer> sumaActual = new DoubleLinkedList<Integer>();
			PositionList<Integer> sumaMinima = new DoubleLinkedList<Integer>();
			sumaActual.addFirst(0);
			sumaMinima.addFirst(0);
			this.caminoEconomico(g,mapeo,d,f,caminoActual,caminoMinimo,sumaActual,sumaMinima);
			return caminoMinimo;
		}
		catch(InvalidKeyException e) {return null;}
	}
	private void caminoEconomico(Graph<String,Integer>gra,Map<Vertex<String>,Boolean>mapeo,Vertex<String>v,Vertex<String>destino,PositionList<Vertex<String>>caminoActual,PositionList<Vertex<String>>caminoMinimo,PositionList<Integer> costoActual,PositionList<Integer> costoMinimo) {
		try {
			mapeo.put(v,true);
			caminoActual.addLast(v);
			if(v == destino) {
				if(costoActual.first().element() < costoMinimo.first().element()) {
					caminoMinimo = caminoActual;
					costoMinimo.set(costoMinimo.first(),costoActual.first().element());
				}
			}	
			else {
				for(Edge<Integer> arc : gra.succesorEdges(v)) {
					Vertex<String> aux = gra.opposite(v,arc);
						if(mapeo.get(aux) == false) {	
							costoActual.set(costoActual.first(),costoActual.first().element()+arc.element());
							caminoEconomico(gra,mapeo,aux,destino,caminoActual,caminoMinimo,costoActual,costoMinimo);		
						}
				}
				
			}
			caminoActual.remove(caminoActual.last());
			mapeo.put(v,false);	
		}
		catch(InvalidPositionException|EmptyListException|InvalidEdgeException|InvalidVertexException|InvalidKeyException e) {}
	}
	public boolean BFSCamino(Graph<String,Integer> g, Vertex<String> v, Vertex<String>d) {
		Map<Vertex<String>,Boolean> mapeo = new OpenHMap<Vertex<String>,Boolean>();
		Map<Vertex<String>,Vertex<String>> mapeoPrev = new OpenHMap<Vertex<String>,Vertex<String>>();
		boolean res = false;
		try {
			for(Vertex<String> w : g.vertices())
				mapeo.put(w,false);
			res= BFSCascara(g,v,d,mapeo,mapeoPrev);
		}
		catch(InvalidKeyException e) {return false;}
		if(res == true) {
			imprimoCamino(v,d,mapeoPrev);
			return res;
		}
		else
			return false;
	}
	private void imprimoCamino(Vertex<String> v, Vertex<String> d, Map<Vertex<String>,Vertex<String>> m) {
		
	}
	private boolean BFSCascara(Graph<String,Integer>g, Vertex<String>v, Vertex<String> d, Map<Vertex<String>,Boolean> mapD,Map<Vertex<String>,Vertex<String>> mapeoPrev) {
		PositionList<Vertex<String>> cola = new DoubleLinkedList<Vertex<String>>();
		cola.addLast(v);
		try {
			while(!cola.isEmpty()) {
				Vertex<String> x = cola.remove(cola.first());
				if(x == d)
					return true;
				for(Vertex<String> w : g.vertices()) {
					if(g.areAdjacent(v, w)) {
						if(mapD.get(w) == false) {
							cola.addLast(w);
							mapD.put(w,true);
							mapeoPrev.put(w,v);
						}
					}
				}	
			}
			return false;
		}
		catch(InvalidKeyException|InvalidVertexException|InvalidPositionException|EmptyListException p) {return false;}
	}
	public boolean existeCamino(Graph<String,Integer> grafo, Vertex<String>origen,Vertex<String> destino) {
		Map<Vertex<String>,Boolean> mapeo = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : grafo.vertices())
				mapeo.put(v,false);
		}
		catch(InvalidKeyException e) {}
		return existe(grafo,origen,destino,mapeo);
	}	
	private boolean existe(Graph<String,Integer>grafo,Vertex<String>origen,Vertex<String>destino,Map<Vertex<String>,Boolean> mapeo) {
		try {
			boolean encontre = false;
			mapeo.put(origen,true);	
			if(origen == destino)
				return true;
			else {
				for(Edge<Integer> e : grafo.succesorEdges(origen)) {
					Vertex<String> adyacente = grafo.opposite(origen,e);
					if(mapeo.get(adyacente) == false)
						 encontre = existe(grafo,adyacente,destino,mapeo);
					if(encontre)
						 return true;
				}
			}
			return false;
		}
		catch(InvalidKeyException|InvalidVertexException|InvalidEdgeException p) {return false;}
	}
	
	public PositionList<Vertex<String>> camino(Graph<String,Integer> grafo, Vertex<String> origen,Vertex<String>destino){
		PositionList<Vertex<String>> camino = new DoubleLinkedList<Vertex<String>>();
		Map<Vertex<String>,Boolean> mapeo = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : grafo.vertices())
				mapeo.put(v,false);
		}
		catch(InvalidKeyException e) {}
		/*
		if(existeCamino(grafo,origen,destino,camino,mapeo))
			return camino;
		else
			return null;
		*/
		return camino;
	}
	private boolean existeCamino(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino, PositionList<Vertex<String>> camino, Map<Vertex<String>,Boolean> mapeo) {
		try {
			mapeo.put(origen,true);
			camino.addLast(origen);
			boolean encontre = false;
			if(origen == destino)
				return true;
			else {
				for(Edge<Integer> e : grafo.succesorEdges(origen)) {
					Vertex<String> adyacente = grafo.opposite(origen,e);
					if(mapeo.get(adyacente) == false)
						encontre = existeCamino(grafo,adyacente,destino,camino,mapeo);
					if(encontre)
						return true;
				}
			}
			camino.remove(camino.last());
			return false;
		}
		catch(EmptyListException|InvalidKeyException|InvalidEdgeException|InvalidVertexException|InvalidPositionException p) {return false;}
	}
	public boolean BFS(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino) {
		Map<Vertex<String>,Boolean> mapeo = new OpenHMap<Vertex<String>,Boolean>();
		PositionList<Vertex<String>> camino = new DoubleLinkedList<Vertex<String>>();
		try {
			for(Vertex<String> v : grafo.vertices())
				mapeo.put(v,false);
		}
		catch(InvalidKeyException e) {}
		return BFS(grafo,origen,destino,camino,mapeo);
	}
	private boolean BFS(Graph<String,Integer>grafo,Vertex<String>origen,Vertex<String>destino,PositionList<Vertex<String>>camino,Map<Vertex<String>,Boolean>mapeo) {
		try {
			camino.addLast(origen);
			mapeo.put(origen,true);
			while(!camino.isEmpty()) {
				Vertex<String> x = camino.remove(camino.first());
				if(x == destino)
					return true;
				else {
					for(Edge<Integer> a : grafo.succesorEdges(x)) {
						Vertex<String> opuesto = grafo.opposite(x,a);
						if(mapeo.get(opuesto) == false) {
							camino.addLast(opuesto);
							mapeo.put(opuesto,true);
						}
					}
				}
			}
			return false;
		}
		catch(InvalidKeyException|InvalidVertexException|InvalidEdgeException|EmptyListException|InvalidPositionException p) {return false;}
	}
	public boolean BFSMapeado(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,PositionList<Vertex<String>> camino) {
		Map<Vertex<String>,Boolean> marcado = new OpenHMap<Vertex<String>,Boolean>();
		Map<Vertex<String>,Vertex<String>> previo = new OpenHMap<Vertex<String>,Vertex<String>>();
		try {
			for(Vertex<String> v : grafo.vertices())
				marcado.put(v,false);
		}
		catch(InvalidKeyException e) {}
	
		 boolean res = BFSMapeado(grafo,origen,destino,camino,marcado,previo);
		 if(res)
			 devuelvoCamino(origen,destino,previo,camino);
		 else
			 camino = new DoubleLinkedList<Vertex<String>>();
		 return res;
	}
	private void devuelvoCamino(Vertex<String> origen, Vertex<String> destino, Map<Vertex<String>,Vertex<String>> previo,PositionList<Vertex<String>>camino) {
		Vertex<String> x = destino;
		try {
			while(x!=null) {
				camino.addFirst(x);
				x = previo.get(x);
			}
		}
		catch(InvalidKeyException p) {}
	}
	private boolean BFSMapeado(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,PositionList<Vertex<String>> lista, Map<Vertex<String>,Boolean>marcado, Map<Vertex<String>,Vertex<String>>previo) {
		try {
			lista.addLast(origen);
			marcado.put(origen,true);
			while(!lista.isEmpty()) {
				Vertex<String> x = lista.remove(lista.first());
				if(x == destino)
					return true;
				else {
					for(Edge<Integer> a : grafo.succesorEdges(x)) {
						Vertex<String> opuesto = grafo.opposite(x,a);
						if(marcado.get(opuesto) == false) {
							lista.addLast(opuesto);
							marcado.put(opuesto,true);
							previo.put(opuesto,x);
						}
					}
				}
			}
			return false;
		}
		catch(InvalidKeyException|InvalidEdgeException|InvalidVertexException|EmptyListException|InvalidPositionException p) {return false;}
	}
	public boolean DFSAgain(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino) {
		Map<Vertex<String>,Boolean> marcado = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : grafo.vertices())
				marcado.put(v,false);
		}
		catch(InvalidKeyException e) {}
		return DFSAgain(grafo,origen,destino,marcado);
	}
	private boolean DFSAgain(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,Map<Vertex<String>,Boolean> marcado){
		try {
			marcado.put(origen,true); boolean encontre = false;
			if(origen == destino)
				return true;
			else {
				for(Edge<Integer> a : grafo.succesorEdges(origen)) {
					Vertex<String> opuesto = grafo.opposite(origen,a);
					if(marcado.get(opuesto) == false) { 
						encontre = DFSAgain(grafo,opuesto,destino,marcado);
						if(encontre)
							return true;
					}
				}
			}
			marcado.put(origen,false);
			return false;
		}
		catch(InvalidKeyException|InvalidEdgeException|InvalidVertexException p) {return false;}
	}
	
	public PositionList<Vertex<String>> dfsCamino(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,PositionList<Vertex<String>>listaCamino){
		Map<Vertex<String>,Boolean> marcado = new OpenHMap<Vertex<String>,Boolean>();
		Map<Vertex<String>,Vertex<String>> previo = new OpenHMap<Vertex<String>,Vertex<String>>();
		try {
			for(Vertex<String> v : grafo.vertices())
				marcado.put(v,false);
		}
		catch(InvalidKeyException p) {}
		boolean res = dfsCamino(grafo,origen,destino,marcado,previo);
		if(res)
			this.armoCamino(origen, destino, previo, listaCamino);
		return listaCamino;
	}
	private boolean dfsCamino(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,Map<Vertex<String>,Boolean> marcado,Map<Vertex<String>,Vertex<String>> previo) {
		try {
			marcado.put(origen,true); boolean encontre = false;
			if(origen == destino)
				return true;
			else {
				for(Edge<Integer> a : grafo.succesorEdges(origen)) {
					Vertex<String> opuesto = grafo.opposite(origen,a);
					if(marcado.get(opuesto) == false) {
						previo.put(opuesto,origen);
						encontre = dfsCamino(grafo,opuesto,destino,marcado,previo);
					}
					if(encontre)
						return true;
				}
			}
			marcado.put(origen,false);
			return false;
		}
		catch(InvalidKeyException|InvalidEdgeException|InvalidVertexException p) {return false;}
	}
	private void armoCamino(Vertex<String> origen, Vertex<String> destino, Map<Vertex<String>,Vertex<String>> previo, PositionList<Vertex<String>>camino) {
		try {
			Vertex<String> x = destino;
			while(x!=null) {
				camino.addFirst(x);
				x = previo.get(x);
			}
		}
		catch(InvalidKeyException p) {}
	}
	public boolean testeando(Graph<String,Integer>grafo,Vertex<String>origen,Vertex<String>destino,PositionList<Vertex<String>>listaCamino) {
		Map<Vertex<String>,Boolean> mapa = new OpenHMap<Vertex<String>,Boolean>();
		try {
			for(Vertex<String> v : grafo.vertices())
				mapa.put(v,false);
		}
		catch(InvalidKeyException p) {}
		return testeando(grafo,origen,destino,listaCamino,mapa);
	}
	private boolean testeando(Graph<String,Integer>grafo,Vertex<String>origen,Vertex<String>destino,PositionList<Vertex<String>>listaCamino,Map<Vertex<String>,Boolean>mapa) {
		try {
			listaCamino.addLast(origen);
			mapa.put(origen,true);
			boolean encontre = false;
			if(origen == destino)
				return true;
			else {
				for(Edge<Integer> a : grafo.succesorEdges(origen)) {
					Vertex<String> opuesto = grafo.opposite(origen,a);
					if(mapa.get(opuesto) == false)
						encontre = testeando(grafo,opuesto,destino,listaCamino,mapa);
					if(encontre)
						return true;
				}
			}
			listaCamino.remove(listaCamino.last());
			return false;
		}
		catch(InvalidPositionException|InvalidKeyException|EmptyListException|InvalidEdgeException|InvalidVertexException z) { return false; }
	}
	public boolean esConexo(Graph<String,Integer> g) {
		for(Vertex<String> v : g.vertices())
			for(Vertex<String> w : g.vertices())
				if(!this.BFS(g,v,w))
					return false;
		return true;
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public boolean BFSAlg(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,Vertex<String>intermedio,PositionList<Vertex<String>> camino) {
		Map<Vertex<String>,Boolean> marcado = new OpenHMap<Vertex<String>,Boolean>();
		Map<Vertex<String>,Vertex<String>> previo = new OpenHMap<Vertex<String>,Vertex<String>>();
		try {
			for(Vertex<String> v : grafo.vertices())
				marcado.put(v,false);
		}
		catch(InvalidKeyException e) {}
	
		 boolean res = BFSx(grafo,origen,destino,intermedio,camino,marcado,previo);
		 if(res)
			 way(origen,destino,previo,camino);
		 else
			 camino = new DoubleLinkedList<Vertex<String>>();
		 return res;
	}
	private void way(Vertex<String> origen, Vertex<String> destino, Map<Vertex<String>,Vertex<String>> previo,PositionList<Vertex<String>>camino) {
		Vertex<String> x = destino;
		try {
			while(x!=null) {
				camino.addFirst(x);
				x = previo.get(x);
			}
		}
		catch(InvalidKeyException p) {}
	}
	private boolean BFSx(Graph<String,Integer> grafo, Vertex<String> origen, Vertex<String> destino,Vertex<String> intermedio,PositionList<Vertex<String>> lista, Map<Vertex<String>,Boolean>marcado, Map<Vertex<String>,Vertex<String>>previo) {
		try {
			lista.addLast(origen);	boolean pase = false;
			marcado.put(origen,true);
			while(!lista.isEmpty()) {
				Vertex<String> x = lista.remove(lista.first());
				if(x == destino && pase)
					return true;
				else {
					for(Edge<Integer> a : grafo.succesorEdges(x)) {
						Vertex<String> opuesto = grafo.opposite(x,a);
						if(marcado.get(opuesto) == false) {
							if(opuesto == intermedio)
								pase = true;
							lista.addLast(opuesto);
							marcado.put(opuesto,true);
							previo.put(opuesto,x);
						}
					}
				}
			}
			return false;
		}
		catch(InvalidKeyException|InvalidEdgeException|InvalidVertexException|EmptyListException|InvalidPositionException p) {return false;}
	}
	
	
	
	
	
	
	
	
	
}

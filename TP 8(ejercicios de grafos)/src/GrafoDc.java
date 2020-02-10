import Mapeo.*;
import Excepciones.*;
import TDALista.*;
import TDACola.*;
public class GrafoDc {
	GraphD<String,Integer>grafo;
	
	public GrafoDc() {
		grafo=new GrafoD<String,Integer>();	
	}
	public Vertex<String> AgregarCiudad(String s) {
		
		return grafo.insertVertex(s); 
	}
	public Edge<Integer> AgregarRuta(Vertex<String> c1,Vertex<String> c2,int k) {
		Edge<Integer>ret=null;
		try {
			ret= grafo.insertEdge(c1, c2, k);
		}
		catch(InvalidVertexException e) {
			e.printStackTrace();
		}
		return ret;
	}
	private static PositionList<Vertex<String>> clonar (PositionList<Vertex<String>> original){
		PositionList<Vertex<String>> copia = new ListaDobleEnlaze<Vertex<String>>();
		for(Vertex<String> v : original) {
			copia.addLast(v);
		}

		return copia;
	}
	public PositionList<Vertex<String>> camino(String p1,String p2,String p3){		
		Par3 camino=new Par3();
		Par3 camino2=new Par3();
		Vertex<String> P1,P2,P3;
		P1=null;P2=null;P3=null;
		for(Vertex<String> v:grafo.vertices()) {
			if(v.element().equals(p1)) {
				P1=v;
			}
			if(v.element().equals(p2)) {
				P2=v;
			}
			if(v.element().equals(p3)) {
				P3=v;
			}
		}
	if(CaminoAux(P1,P2,camino)) {
		try {
			camino.getCamino().remove(camino.getCamino().last());
		} catch (InvalidPositionException | EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(CaminoAux(P2,P3,camino2)) {
			for(Vertex<String>pos:camino2.getCamino()) {
				camino.getCamino().addLast(pos);
			}
		}
		else {
			camino.setCamino(new ListaDobleEnlaze<Vertex<String>>());
		}
		return camino.getCamino();
	}
	else {
		return new ListaDobleEnlaze<Vertex<String>>();
	}
	}
	private boolean CaminoAux(Vertex<String> p1,Vertex<String> p2,Par3 camino) {
		Map<Vertex<String>,PositionList<Vertex<String>>> visitados=new MapHash<Vertex<String>,PositionList<Vertex<String>>>(); 
		Queue<Vertex<String>> cola=new ColaCircular<Vertex<String>>();
		Vertex<String> a_considerar=null;
		boolean encontre=false;
		cola.enqueue(p1);
		try {
			visitados.put(p1,new ListaDobleEnlaze<Vertex<String>>());
			visitados.get(p1).addLast(p1);
			while(!cola.isEmpty()&&!encontre) {
				a_considerar=cola.dequeue();
				if(a_considerar==p2) {
					encontre=true;
				}
				else {
					for(Edge<Integer>a_incidentes:grafo.succesorEdges(a_considerar)) {
						Vertex<String>opuesto=grafo.opposite(a_considerar, a_incidentes);
						if(visitados.get(opuesto)==null) {
							PositionList<Vertex<String>>camino_a_opuesto=clonar(visitados.get(a_considerar));
							camino_a_opuesto.addLast(opuesto);
							cola.enqueue(opuesto);
							visitados.put(opuesto,camino_a_opuesto);
						}
						
					}
				}
				
			}
		}
		catch( InvalidKeyException | InvalidVertexException | InvalidEdgeException | EmptyQueueException e) {
			e.printStackTrace();
		}
		if(encontre) {
			try {
				camino.setCamino(clonar(visitados.get(a_considerar)));
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else {
			camino.setCamino(new ListaDobleEnlaze<Vertex<String>>());
			return false;
		}
	}









}

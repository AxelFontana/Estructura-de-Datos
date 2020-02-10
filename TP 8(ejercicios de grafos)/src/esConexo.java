import Excepciones.*;
import TDALista.*;
import Mapeo.*;
import java.util.Iterator;
public class esConexo {
	public static void main(String[] args) {

		// Creo el grafo.
		Graph<Character,Integer> G = new Grafo<Character,Integer>();

		// Inserto algunos Vertices.
		Vertex<Character> A = G.insertVertex('a');
		Vertex<Character> B = G.insertVertex('b');
		Vertex<Character> C = G.insertVertex('c');
		Vertex<Character> D = G.insertVertex('d');
		Vertex<Character> E = G.insertVertex('e');
		Vertex<Character> F = G.insertVertex('f');
		Vertex<Character> H = G.insertVertex('h');
		Vertex<Character> Z = G.insertVertex('z');
		Vertex<Character> X = G.insertVertex('x');
		// Inserto algunos arcos con su peso.
		try {
			Edge<Integer> ar1 = G.insertEdge(A, B, 1);
			Edge<Integer> ar2 = G.insertEdge(A, E, 100);
			Edge<Integer> ar3 = G.insertEdge(A, C, 60);
			Edge<Integer> ar4 = G.insertEdge(C, D, 60);
			Edge<Integer> ar5 = G.insertEdge(D, E, 60);
			Edge<Integer> ar6 = G.insertEdge(B, E, 1);
			Edge<Integer> ar7 = G.insertEdge(B, D, 50);
			Edge<Integer>ar8=G.insertEdge(F, H, 3);
			Edge<Integer>ar9=G.insertEdge(Z, X, 3);
		} catch (InvalidVertexException e) {e.printStackTrace();}

		// Calculo el camino más económico y lo muestro.
		boolean resultado = esConexo1(G);
		System.out.println(resultado);
		for(Entry<Vertex<Character>,Integer>entrada:componentesConexos(G).entries()) {
			System.out.println("Vertice:"+entrada.getKey().element()+" Grupo:"+entrada.getValue());
		}
		
	}
	public static boolean esConexo1(Graph<Character,Integer> g) {
		Map<Vertex<Character>,Boolean> visitados=new MapHash<Vertex<Character>,Boolean>();
		Iterator<Vertex<Character>>it=g.vertices().iterator();
		Vertex<Character>pos=it.hasNext()?it.next():null;
		
		for(Vertex<Character> vertice:g.vertices()) {
			try {
				visitados.put(vertice, false);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DFS(g,pos,visitados);
		
		boolean es=true;
		
		while(es && pos!=null) {
			try {
				if(visitados.get(pos)==false) {
					es=false;
				}
				else {
					pos=it.hasNext()?it.next():null;
				}
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return es;
	}
	public static void DFS(Graph<Character,Integer> g,Vertex<Character> origen,Map<Vertex<Character>,Boolean> visitados) {
		try {
			visitados.put(origen,true);
			Vertex<Character>opuesto;
			for(Edge<Integer>arco:g.incidentEdges(origen)) {
				opuesto=g.opposite(origen,arco);
				
				if(visitados.get(opuesto)==false) {
					DFS(g,opuesto,visitados);
				}
			}
			
			
			
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Map<Vertex<Character>,Integer> componentesConexos(Graph<Character,Integer>g){
		Map<Vertex<Character>,Integer> conexos=new MapHash<Vertex<Character>,Integer>();
		
		for(Vertex<Character> vert:g.vertices()) {
			try {
				conexos.put(vert, 0);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		Integer grupo=0;
		for(Vertex<Character>vert:g.vertices()) {
			try {
				if(conexos.get(vert)==0) {
					grupo++;
					crearGrupo(vert,grupo,g,conexos);
					
				}
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conexos;
	}
	
	
	private static void crearGrupo(Vertex<Character>origen,Integer grupo,Graph<Character,Integer> g,Map<Vertex<Character>,Integer>conexos) {
		try {
			conexos.put(origen, grupo);
			Vertex<Character>opuesto;
			
			for(Edge<Integer>ed:g.incidentEdges(origen)) {
				opuesto=g.opposite(origen, ed);
				if(conexos.get(opuesto)==0) {
					crearGrupo(opuesto,grupo,g,conexos);
				}
			}
			
			
			
			
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}

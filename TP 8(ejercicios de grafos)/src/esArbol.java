
import Excepciones.*;
import Mapeo.*;
import TDALista.PositionList;
public class esArbol {
	public static boolean isTree(GraphD<Character,Integer> g,Vertex<Character> vert) {
		Map<Vertex<Character>,Boolean> visitados=new MapHash<Vertex<Character>,Boolean>();
		Boolean t=true;
		try {
		for(Vertex<Character>v:g.vertices()) {
			visitados.put(v, false);
		}
		}
		catch(InvalidKeyException ee) {
			ee.printStackTrace();
		}
		return isTreeaux(visitados,g,vert,t);
	}
	private static boolean isTreeaux(Map<Vertex<Character>,Boolean> visitados,GraphD<Character,Integer> g,Vertex<Character> vert,Boolean t) {
		Vertex<Character> op=null;
		try {
		visitados.put(vert, true);
		
		if(t==true) {
		for(Edge<Integer> e:g.succesorEdges(vert)) {
			op=g.opposite(vert, e);
			if(visitados.get(op)==true) {
				t=false;
				//break;
			}
			t=isTreeaux(visitados,g,op,t);
		}
		}
		else {
			return false;
		}
		}
		catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException  ee) {
			ee.printStackTrace();
		}
		
		return t;
	}
	
	public static void main(String[] args) {

		// Creo el grafo.
		GraphD<Character,Integer> G = new GrafoD<Character,Integer>();

		// Inserto algunos Vertices.
		Vertex<Character> A = G.insertVertex('a');
		Vertex<Character> B = G.insertVertex('b');
		Vertex<Character> C = G.insertVertex('c');
		Vertex<Character> D = G.insertVertex('d');
		Vertex<Character> E = G.insertVertex('e');

		// Inserto algunos arcos con su peso.
		try {
			Edge<Integer> ar1 = G.insertEdge(A, B, 1);
			Edge<Integer> ar2 = G.insertEdge(A, E, 100);
		Edge<Integer> ar3 = G.insertEdge(A, C, 60);
			Edge<Integer> ar4 = G.insertEdge(C, D, 60);
		//Edge<Integer> ar5 = G.insertEdge(D, E, 60);
			//Edge<Integer> ar6 = G.insertEdge(B, C, 1);
			Edge<Integer> ar7 = G.insertEdge(B, D, 50);

		} catch (InvalidVertexException e) {e.printStackTrace();}

		// Calculo el camino más económico y lo muestro.
		boolean resultado = isTree(G, A);
		System.out.println(resultado);
		
	}
} 
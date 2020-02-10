
import TDALista.*;

import Excepciones.*;
import Mapeo.*;
import TDACola.*;
public class BFS {
	public static boolean bfs(Vertex<Character> v,Vertex<Character> w,Graph<Character,Integer> g,Par p) {
		PositionList<Vertex<Character>> caminoaux=new ListaDobleEnlaze<Vertex<Character>>();
		int pesoaux=0;
		MapHash<Vertex<Character>,Boolean> visitados=new MapHash<Vertex<Character>,Boolean>();
		for(Vertex<Character> vert:g.vertices()) {
			try {
				visitados.put(vert, false);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bfsaux(v,w,g,caminoaux,pesoaux,visitados,p);
		if(p.getCamino().size()>0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	private static void bfsaux(Vertex<Character> origen,Vertex<Character> destino,Graph<Character,Integer> g,
								 PositionList<Vertex<Character>> caminoaux,int pesoaux,MapHash<Vertex<Character>,Boolean> visitados,Par p) {
		try {
			caminoaux.addLast(origen);
			pesoaux++;
		visitados.put(origen, true);
		Vertex<Character> op=null;
		for(Edge<Integer> e:g.incidentEdges(origen)) {
			op=g.opposite(origen, e);
			if(op==destino) {
				caminoaux.addLast(op);
				pesoaux++;
				if(pesoaux>p.getPeso()) {
					p.setCamino(clonar(caminoaux));
					p.setPeso(pesoaux);
				}
				caminoaux.remove(caminoaux.last());
				pesoaux--;
			}
			else {
				if(visitados.get(op)==false) {
					bfsaux(op,destino,g,caminoaux,pesoaux,visitados,p);
				}
			}
			
		}
		caminoaux.remove(caminoaux.last());
		pesoaux--;
		visitados.put(origen, false);
		}
		catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException ee) {
			ee.printStackTrace();
		}
	}
	private static PositionList<Vertex<Character>> clonar (PositionList<Vertex<Character>> original){
		PositionList<Vertex<Character>> copia = new ListaDobleEnlaze<Vertex<Character>>();
		for(Vertex<Character> v : original) {
			copia.addLast(v);
		}

		return copia;
	}
	public static boolean cmc(Graph<Character,Integer>g,Vertex<Character>o,Vertex<Character>d,Par4 camino) {
		boolean encontre=false;
		//cola que mantiene los vertices ordenados en anchura a ser visitados
		
		Queue<Vertex<Character>>cola=new ColaCircular<Vertex<Character>>();
		
		// Mapeo que indica si un nodo fue visitado y en tal caso el camino desde origen hasta llegar a el
		
		Map<Vertex<Character>,PositionList<Vertex<Character>>> visitados= new MapHash<Vertex<Character>,PositionList<Vertex<Character>>>();
		for(Vertex<Character> v:g.vertices()) {
			try {
				visitados.put(v, null);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Vertice auxiliar
		
		Vertex<Character> a_considerar=null;
		cola.enqueue(o);
		try {		
			visitados.put(o,new ListaDobleEnlaze<Vertex<Character>>());
			visitados.get(o).addLast(o);
		//marque el primero como visitado y indique que el camino del origen a el es el
			
			while(!cola.isEmpty()&&!encontre) {
				a_considerar=cola.dequeue();
				//si es el que hay que encontrar
				if(a_considerar==d) {
					encontre=true;
				}
				else {
					//Se itera sobre cada arco incidente sobre el vertice a considerar 
					for(Edge<Integer> a_incidente: g.incidentEdges(a_considerar)) {
						//se obtiene el vertice opuesto a a_considerar
						Vertex<Character>opuesto=g.opposite(a_considerar, a_incidente);
						//Pregunto si no esta visitado
						if(visitados.get(opuesto)==null) {
							//se construye el camino para llegar al vertice opuesto 
							// vertice a considerar+vertice opueto
							PositionList<Vertex<Character>>caminoop=clonar(visitados.get(a_considerar));
							caminoop.addLast(opuesto);
							//se encola e indica el opuesto como visitado indicando el camino para llegar a el
							cola.enqueue(opuesto);
							visitados.put(opuesto,caminoop);
						}
					}
				}
			}
			
			
		}
		catch(InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		if(encontre) {
			try {
				camino.setCamino(visitados.get(a_considerar));
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else {
			camino.setCamino(new ListaDobleEnlaze<Vertex<Character>>());
			return false;
		}
	}
	
	public static void main(String[] args) {

		// Creo el grafo.
		Graph<Character,Integer> G = new Grafo<Character,Integer>();

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
			Edge<Integer> ar5 = G.insertEdge(D, E, 60);
			Edge<Integer> ar6 = G.insertEdge(B, E, 1);
			Edge<Integer> ar7 = G.insertEdge(B, D, 50);

		} catch (InvalidVertexException e) {e.printStackTrace();}

		// Calculo el camino más económico y lo muestro.
		Par p=new Par();
		/*boolean resultado = bfs(A, E, G,p);
		System.out.println(resultado);
		for(Vertex<Character> vertice:p.getCamino()) {
			System.out.print(vertice.element()+" --> ");
		}*/
		Par4 camino=new Par4();
		boolean hay=cmc(G, A, D, camino);
		System.out.println(hay);
		for(Vertex<Character> vertice2:camino.getCamino()) {
			System.out.print(vertice2.element()+" ---> ");
		}
	}

}
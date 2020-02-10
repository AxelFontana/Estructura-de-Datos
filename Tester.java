package MetodosParcial;

import GrafoDListaAdyacente.Edge;
import GrafoDListaAdyacente.GrafoDListaAdyacente;
import GrafoDListaAdyacente.Graph;
import GrafoDListaAdyacente.InvalidVertexException;
import GrafoDListaAdyacente.InvalidEdgeException;
import GrafoDListaAdyacente.Vertex;
import listaDoble.*;

public class Tester {
	
			public static void main(String[]args) {
				Graph<String,Integer> grafo = new GrafoDListaAdyacente<String,Integer>();
				Vertex<String> bb = grafo.insertVertex("Bahia Blanca");
				Vertex<String> cp = grafo.insertVertex("Coronel Pringles");
				Vertex<String> cz = grafo.insertVertex("Coronel Suarez");
				Vertex<String> ba = grafo.insertVertex("Buenos Aires");
				Vertex<String> ig = grafo.insertVertex("Ing.White");
				Vertex<String> az = grafo.insertVertex("Azul");
				Vertex<String> lp = grafo.insertVertex("La Plata");
				Vertex<String> mp = grafo.insertVertex("Mar Del Plata");
				Vertex<String> dd = grafo.insertVertex("Dorrego");
				try {
					Edge<Integer> czcp = grafo.insertEdge(cz,cp,85);
					Edge<Integer> cpcz = grafo.insertEdge(cp,cz,85);
					
					Edge<Integer> cpdd = grafo.insertEdge(cp,dd,120);
					Edge<Integer> ddbb = grafo.insertEdge(dd,bb,100);
					
					Edge<Integer> bbcp = grafo.insertEdge(bb,cp,127);
					Edge<Integer> cpbb = grafo.insertEdge(cp,bb,127);
					
					Edge<Integer> czbb = grafo.insertEdge(cz,bb,240);
					Edge<Integer> bbcz = grafo.insertEdge(bb,cz,240);
					Edge<Integer> bbig = grafo.insertEdge(bb,ig,28);
					Edge<Integer> cpaz = grafo.insertEdge(cp,az,239);
					Edge<Integer> azba = grafo.insertEdge(az,ba,310);
					Edge<Integer> balp = grafo.insertEdge(ba,lp,80);
					Edge<Integer> bamp = grafo.insertEdge(ba,mp,410);
					Edge<Integer> azmp = grafo.insertEdge(az,mp,290);

					Metodos aux = new Metodos();
					PositionList<Vertex<String>> listaCamino = new DoubleLinkedList<Vertex<String>>();
					
					boolean hay = aux.BFSAlg(grafo,cp,bb,dd,listaCamino);
					
					System.out.println(hay);
					
					/*
					if(aux.testeando(grafo,cp,lp, listaCamino)) {
						for(Vertex<String> v : listaCamino)
							System.out.print(v.element().toString()+"-");
					}
					else
						System.out.println("false");
					*/
				}	
				catch(InvalidVertexException w) {}
			}
	}

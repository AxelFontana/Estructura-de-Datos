package TDAGrafo.ListaAdyacencia;

import TDAColaCircular.ColaCircular;
import TDAColaCircular.Queue;
import TDALista.ListaDobleEnlaze;
import TDALista.PositionList;
import Exceptions.*;
import TDAMapeo.*;

public class EjerciciosPractica2 {

    public static void main(String[] args){

        // Creo el grafo.
        GraphD<Character,Integer> G = new GrafoD<Character,Integer>();

        // Inserto algunos Vertices.
        Vertex<Character> A = G.insertVertex('a');
        Vertex<Character> B = G.insertVertex('b');
        Vertex<Character> D = G.insertVertex('d');
        Vertex<Character> H = G.insertVertex('h');
        Vertex<Character> E = G.insertVertex('e');
        Vertex<Character> I = G.insertVertex('i');
        Vertex<Character> J = G.insertVertex('j');
        Vertex<Character> C = G.insertVertex('c');
        Vertex<Character> F = G.insertVertex('f');
        Vertex<Character> Ñ = G.insertVertex('ñ');
        Vertex<Character> K = G.insertVertex('k');

        // Inserto algunos arcos con su peso.
        try {
            Edge<Integer> ar1 = G.insertEdge(A, B, 30);
            Edge<Integer> ar2 = G.insertEdge(A, C, 33);
            Edge<Integer> ar3 = G.insertEdge(B, D, 1);
            Edge<Integer> ar4 = G.insertEdge(B, E, 20);
            Edge<Integer> ar5 = G.insertEdge(D, H, 60);
            Edge<Integer> ar6 = G.insertEdge(E, I , 30);
            Edge<Integer> ar7 = G.insertEdge(E, J, 50);
            Edge<Integer> ar8 = G.insertEdge(C, F, 50);
            Edge<Integer> ar9 = G.insertEdge(F, K, 50);
            Edge<Integer> ar10 = G.insertEdge(C, Ñ, 50);
        } catch (InvalidVertexException e) {e.printStackTrace();}

        // Calculo el camino más económico y lo muestro.
       /* PositionList<Vertex<Character>> resultado = caminoEconomico(A, D, G);
        System.out.println("El camino es: ");
        for(Vertex<Character> v : resultado) {
            System.out.print(" " + v.element() + " ");
        }
        */
        mostrarCaminoBFS(G);
    }

    public static void mostrarCaminoDFS(GraphD<Character, Integer> g){
        Map<Vertex<Character>, Boolean> visitados = new mapeoConHashAbierto<Vertex<Character>, Boolean>();
        try{
            for (Vertex<Character> v : g.vertices()){
                visitados.put(v, false);
            }
            System.out.println("Camino DFS :");
            for(Vertex<Character> v : g.vertices()){
                if(visitados.get(v) == false){
                    caminoDFS(g, visitados, v);
                }
            }
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
        }
    }

    public static void caminoDFS(GraphD<Character, Integer> g, Map<Vertex<Character>, Boolean> visitados, Vertex<Character> origen){
        System.out.print(origen.element()+"-");
        try{
            visitados.put(origen,true);
            //Para cada vertice adyacente a origen
            for(Edge<Integer> arc : g.succesorEdges(origen)){
                Vertex<Character> w = g.opposite(origen,arc);
                if(!visitados.get(w)){
                    caminoDFS(g, visitados, w);
                }
            }
        }
        catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e){
            e.printStackTrace();
        }
    }
    public static void mostrarCaminoBFS(GraphD<Character,Integer> g) {
        Map<Vertex<Character>, Boolean> visitados = new mapeoConHashAbierto<Vertex<Character>, Boolean>();
        try{
            for(Vertex<Character> v : g.vertices() ){
                visitados.put(v, false);
            }

            for(Vertex<Character> v : g.vertices()){
                if(visitados.get(v) == false){
                    caminoBFS(g, visitados, v);
                }
            }
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
        }
    }

    public static void caminoBFS(GraphD<Character, Integer> g, Map<Vertex<Character>, Boolean> visitados, Vertex<Character> v){
        Queue<Vertex<Character>> cola = new ColaCircular<Vertex<Character>>();
        cola.enqueue(v);
        Vertex<Character> w;
        try{
            while(!cola.isEmpty()){
                w = cola.dequeue();
                if(!visitados.get(w)){
                    System.out.print(w.element()+"-");
                    visitados.put(w, true);
                }
                for(Edge<Integer> arc : g.succesorEdges(w)){
                    if(!visitados.get(g.opposite(w, arc))){
                        cola.enqueue(g.opposite(w, arc));
                    }
                }
            }
        }
        catch (EmptyQueueException | InvalidKeyException | InvalidEdgeException | InvalidVertexException e){
            e.printStackTrace();
        }
    }

    private static PositionList<Vertex<Character>> clonar (PositionList<Vertex<Character>> original){
        PositionList<Vertex<Character>> copia = new ListaDobleEnlaze<Vertex<Character>>();
        for(Vertex<Character> v : original) {
            copia.addLast(v);
        }

        return copia;
    }

    public static PositionList<Vertex<Character>> caminoCorto(Vertex<Character> origen, Vertex<Character> destino, GraphD<Character, Integer> g){
        PositionList<Vertex<Character>> caminoAux = new ListaDobleEnlaze<>();
        Map<Vertex<Character>, Boolean> visitados = new mapeoConHashAbierto<Vertex<Character>, Boolean>();

        Integer pesoAux = 0;
        Par p = new Par();
        try{
            for(Vertex<Character> v : g.vertices()){
                visitados.put(v, false);
            }
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
        }
        caminoCortoAux(origen, destino, g, visitados, p, caminoAux, pesoAux);
        return p.getCamino();
    }
    private static void caminoCortoAux(Vertex<Character> origen, Vertex<Character> destino,GraphD<Character, Integer> g,
                                       Map<Vertex<Character>, Boolean> visitados, Par caminoCorto,PositionList<Vertex<Character>>caminoAux, Integer pesoAux){

        try{
            visitados.put(origen, true);
            caminoAux.addLast(origen);

            Vertex<Character> op = null; // Mantiene un auxiliar de opuesto a origen
            for(Edge<Integer> arc : g.succesorEdges(origen)){
                op = g.opposite(origen, arc);

                if(op == destino){
                    caminoAux.addLast(op);
                    pesoAux++;

                    if(pesoAux < caminoCorto.getPeso()){
                        caminoCorto.setPeso(pesoAux);
                        caminoCorto.setCamino(clonar(caminoAux));
                    }
                    // BackTracking de el nodo destino
                    caminoAux.remove(caminoAux.last());
                    pesoAux--;
                }
                else{
                    if(!visitados.get(op)){
                        caminoCortoAux(op, destino, g, visitados, caminoCorto, caminoAux, pesoAux+1);
                    }
                }
            }
            //BackTracking de un nodo no destino
            caminoAux.remove(caminoAux.last());
            visitados.put(origen, false); // desmarca por si se llega a este nodo mediante otro camino

        }
        catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException
                | InvalidPositionException | EmptyListException e){
            e.printStackTrace();
        }

    }
}
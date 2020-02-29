package TDAGrafo.ListaAdyacencia;

import TDAColaCircular.ColaCircular;
import TDAColaCircular.Queue;
import TDALista.ListaDobleEnlaze;
import TDALista.PositionList;
import Exceptions.*;
import TDAMapeo.*;

public class Ejercicios {

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

    public static PositionList<Vertex<Character>> caminoCorto(Vertex<Character> v1, Vertex<Character> v2, GraphD<Character, Integer> g){
        PositionList<Vertex<Character>> caminoAux = new ListaDobleEnlaze<Vertex<Character>>();
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
        caminoCortoAux(v1, v2, g, visitados, p, caminoAux, pesoAux);
        return p.getCamino();
    }

    private static void caminoCortoAux(Vertex<Character> origen, Vertex<Character> destino,GraphD<Character, Integer> g,
                                   Map<Vertex<Character>, Boolean> visitados, Par caminoCorto,PositionList<Vertex<Character>>caminoAux, Integer pesoAux){

        try{
            visitados.put(origen, true);
            caminoAux.addLast(origen);

            Vertex<Character> op = null; //Mantiene un auxiliar de opuesto a origen
            for(Edge<Integer> ed : g.succesorEdges(origen)){ //Para cada uno de los sucesores del nodo origen
                op = g.opposite(origen, ed);

                if(op == destino){
                    caminoAux.addLast(op);
                    pesoAux++;

                    if(pesoAux < caminoCorto.getPeso()){
                        caminoCorto.setPeso(pesoAux);
                        caminoCorto.setCamino(clonar(caminoAux));
                    }

                    caminoAux.remove(caminoAux.last()); //Para volver al nodo anterior
                    pesoAux--;
                }
                else{
                    if(visitados.get(op) == false){
                        caminoCortoAux(op, destino ,g , visitados, caminoCorto ,caminoAux,pesoAux+1);
                    }
                }
            }
            caminoAux.remove(caminoAux.last());
            visitados.put(origen, false);

        }
        catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException e){
            e.printStackTrace();
        }

    }

    public static PositionList<Vertex<Character>> caminoEconomico(Vertex<Character> v1, Vertex<Character> v2, GraphD<Character, Integer> g){
        PositionList<Vertex<Character>> caminoAux = new ListaDobleEnlaze<Vertex<Character>>();
        Integer pesoAux = 0;
        Par p = new Par();
        Map<Vertex<Character>, Boolean> visitados = new mapeoConHashAbierto<Vertex<Character>, Boolean>();

        try {
            for(Vertex<Character> v : g.vertices()){
                visitados.put(v, false);
            }
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
        }
        caminoEconomicoAux(v1, v2, g, visitados, p, caminoAux, pesoAux);
        return p.getCamino();
    }

    private static void caminoEconomicoAux(Vertex<Character> origen, Vertex<Character> destino, GraphD<Character,Integer> G,
                                           Map<Vertex<Character>, Boolean> visitados, Par caminoCorto, PositionList<Vertex<Character>> caminoAux, Integer pesoAux){

        try {
            caminoAux.addLast(origen);
            visitados.put(origen, true);

            Vertex<Character> op = null;

            for(Edge<Integer> ed : G.succesorEdges(origen)) {

                op = G.opposite(origen, ed);
                if(op == destino){

                    caminoAux.addLast(op);
                    pesoAux = pesoAux + ed.element();

                    if(pesoAux <= caminoCorto.getPeso()) {
                        //OJO! DEBEN GUARDAR UN CLON DE caminoAux, de lo contrario se guarda una referecia, y lo guardado cambia cuando se cambia caminoAux.
                        caminoCorto.setCamino(clonar(caminoAux));
                        caminoCorto.setPeso(pesoAux);
                    }
                    caminoAux.remove(caminoAux.last());
                    pesoAux = pesoAux - ed.element();
                }
                else {
                    if(visitados.get(op) == false) {


                        caminoEconomicoAux(op, destino, G, visitados, caminoCorto, caminoAux, pesoAux+ed.element());

                    }
                }
            }

            caminoAux.remove(caminoAux.last());
            visitados.put(origen, false);


        }catch (InvalidKeyException e1) {e1.printStackTrace();}
        catch (InvalidEdgeException e2) {e2.printStackTrace();}
        catch (InvalidVertexException e3) {e3.printStackTrace();}
        catch (InvalidPositionException e4) {e4.printStackTrace();}
        catch (EmptyListException e5) {e5.printStackTrace();}

    }

   private static PositionList<Vertex<Character>> clonar (PositionList<Vertex<Character>> original){
        PositionList<Vertex<Character>> copia = new ListaDobleEnlaze<Vertex<Character>>();
        for(Vertex<Character> v : original) {
            copia.addLast(v);
        }

        return copia;
    }

    public static void mostrarCaminoDFS(GraphD<Character, Integer> g){
       Map<Vertex<Character>, Boolean> visitados = new mapeoConHashAbierto<Vertex<Character>, Boolean>();
       try{
          for(Vertex<Character> vert : g.vertices()){
              visitados.put(vert, false);
          }
          System.out.println("Camino DFS: ");
          for(Vertex<Character> vert : g.vertices()){
              if(visitados.get(vert) == false){
                  caminoDFS(g, visitados, vert);
              }
          }
        }catch (InvalidKeyException e){
           e.printStackTrace();
       }
    }

    private static void caminoDFS(GraphD<Character, Integer> g, Map<Vertex<Character>, Boolean> visitados, Vertex<Character> origen){
        System.out.print(origen.element()+"-");
        try{
            visitados.put(origen, true);
            //Para cada vertice adyacente a origen
            for(Edge<Integer> arc: g.succesorEdges(origen)){
                Vertex<Character> w = g.opposite(origen, arc);
                if(visitados.get(w) == false){
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
    private static void caminoBFS(GraphD<Character, Integer> g, Map<Vertex<Character>, Boolean> visitados, Vertex<Character> v){
        Queue<Vertex<Character>> cola = new ColaCircular<Vertex<Character>>();
        cola.enqueue(v);
        Vertex<Character> w;
        try{
            while(!cola.isEmpty()){
                w = cola.dequeue();
                if(visitados.get(w) == false){
                    System.out.print(w.element()+"-");
                    visitados.put(w, true);
                }
                for(Edge<Integer> arc : g.succesorEdges(w)){
                    if(visitados.get(g.opposite(w, arc)) == false){
                        cola.enqueue(g.opposite(w, arc));
                    }
                }
            }
        }
        catch (EmptyQueueException | InvalidKeyException | InvalidVertexException | InvalidEdgeException e){
            e.printStackTrace();;
        }



    }
}

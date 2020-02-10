package TDAGrafo.ListaAdyacencia;

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
        Vertex<Character> C = G.insertVertex('c');
        Vertex<Character> D = G.insertVertex('d');
        Vertex<Character> E = G.insertVertex('e');

        // Inserto algunos arcos con su peso.
        try {
            Edge<Integer> ar1 = G.insertEdge(A, B, 30);
            Edge<Integer> ar2 = G.insertEdge(A, C, 33);
            Edge<Integer> ar3 = G.insertEdge(A, E, 1);
            Edge<Integer> ar4 = G.insertEdge(C, D, 20);
            Edge<Integer> ar5 = G.insertEdge(D, E, 60);
            Edge<Integer> ar6 = G.insertEdge(B,E , 30);
            Edge<Integer> ar7 = G.insertEdge(B, D, 50);

        } catch (InvalidVertexException e) {e.printStackTrace();}

        // Calculo el camino más económico y lo muestro.
        PositionList<Vertex<Character>> resultado = caminoEconomico(A, D, G);
        System.out.println("El camino es: ");
        for(Vertex<Character> v : resultado) {
            System.out.print(" " + v.element() + " ");
        }
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

}

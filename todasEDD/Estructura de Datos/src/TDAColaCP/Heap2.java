package TDAColaCP;

import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;

import java.util.Comparator;

public class Heap2<K, V> implements PriorityQueue<K, V> {
    protected Entrada<K, V> [] elems;
    protected Comparator<K> comp;
    protected int size;

    public Heap2(Comparator<K> comp){
        elems = (Entrada<K, V> [])new Entrada [10000];
        this.comp = comp;
        size = 0;
    }
    /**
     * Consulta la cantidad de elementos de la cola.
     * @return Cantidad de elementos de la cola.
     */
    public int size(){
        return size;
    }

    /**
     * Consulta si la cola está vacía.
     * @return Verdadero si la cola está vacía, falso en caso contrario.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Devuelve la entrada con menor prioridad de la cola.
     * @return Entrada con menor prioridad.
     * @throws EmptyPriorityQueueException si la cola está vacía.
     */
    public Entry<K,V> min()throws EmptyPriorityQueueException{
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacia");
        }
        return elems[1]; // no se usa el primer componente del arreglo
    }

    /**
     * Inserta un par clave-valor y devuelve la entrada creada.
     * @param key Clave de la entrada a insertar.
     * @param value Valor de la entrada a insertar.
     * @return Entrada creada.
     * @throws InvalidKeyException si la clave es inválida.
     */
    public Entry<K,V> insert(K key,V value)throws InvalidKeyException{
        if(key == null){
            throw new InvalidKeyException("clave invalida");
        }
        Entrada<K, V> nuevo = new Entrada<K, V>(key, value);
        elems[++size] = nuevo;
        //Burbujeo para arriba
        int i = size;// Indica la posicion de entrada del arreglo
        boolean seguir = true;
        while(i > 1 && seguir){
            Entrada<K, V> elemActual = elems[i];
            Entrada<K, V> elemPadre = elems[i / 2];
            if(comp.compare(elemActual.getKey(), elemPadre.getKey()) < 0){
                Entrada<K, V> aux = elems[i];
                elems[i] = elems[i / 2];
                elems[i /2] = aux;
                i /= 2; //subo hasta la posicion del padre
            }
            else
                seguir = false;//arbol ordenado
        }
        return nuevo;
    }

    /**
     * Remueve y devuelve la entrada con menor prioridad de la cola.
     * @return Entrada con menor prioridad.
     * @throws EmptyPriorityQueueException si la cola está vacía.
     */
    public Entry<K,V> removeMin()throws EmptyPriorityQueueException{
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacia");
        }
        Entry<K, V> ret = elems[1];
        if(size == 1){
            elems[1] = null;
            size =0;
        }
        else{
            //Paso el ultimo a la raiz y lo borro del final
            elems[1] = elems[size];
            elems[size] = null;
            size--;
            //Burbujeo la raiz hacia abajo
            int i = 1;//Estoy parado en la raiz
            boolean seguir = true;
            while(seguir){
                //calculo la posicion de los hijos
                int hi = i * 2;
                int hd = i * 2 + 1;
                boolean tieneHijoIzquierdo = hi < size;
                boolean tieneHijoDerecho = hd < size;
                if (!tieneHijoIzquierdo){
                    seguir = false;
                }
                else{
                    int m; //menor de los hijos
                    if(tieneHijoDerecho){
                        //calculo cual es el menor de los hijos
                        if(comp.compare(elems[hi].getKey(), elems[hd].getKey()) < 0)
                            m = hi;
                        else
                            m = hd;
                    }
                    else//no tiene hijo derecho pero si izquierdo
                        m = hi;

                    //me fijo si hay que intercambiar actual con el menor de sus hijos
                    if(comp.compare(elems[i].getKey(), elems[m].getKey()) > 0){
                        Entrada<K, V> aux = elems[i];
                        elems[i] = elems[m];
                        elems[m] = aux;
                        i = m; //actualizamos a partir de m para seguir burbujeo que ordena
                    }
                    else
                        seguir = false;
                }
            }
        }
        return ret;
    }
}

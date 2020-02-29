package TDAColaCircular;


import Exceptions.EmptyQueueException;

public class ColaCircular<E> implements Queue<E> {

    protected int Inicio,Final;
    protected E cola [];
    protected static final int longitud=10;

    public ColaCircular(){
        cola=(E[])new Object[longitud];
        Inicio=0;
        Final=0;
    }
    /**
     * Devuelve la cantidad de elementos en la cola.
     * @return Cantidad de elementos en la cola.
     */
    public int size(){
        return (cola.length-Inicio+Final)%cola.length;
    }

    /**
     * Consulta si la cola est� vac�a.
     * @return Verdadero si la cola est� vac�a, falso en caso contrario.
     */
    public boolean isEmpty(){
        return Inicio==Final;
    }

    /**
     * Inspecciona el elemento que se encuentra en el frente de la cola.
     * @return Elemento que se encuentra en el frente de la cola.
     * @throws EmptyQueueException si la cola est� vac�a.
     */
    public E front() throws EmptyQueueException{
        if(isEmpty()){
            throw new EmptyQueueException("Cola vacia");
        }
        return cola[Inicio];
    }


    private E[] copiar(int m){
        E []aux=(E[])new Object[2*cola.length];
        for(int i=0;i<size();i++){
            aux[i]=cola[m];
            m=(m+1)%cola.length;
        }
    return aux;
    }
    /**
     * Inserta un elemento en el fondo de la cola.
     * @param element Nuevo elemento a insertar.
     */
    public void enqueue(E element) {
        if (cola.length - 1 == size()) { //La cola esta llena
            E ColaAux[] = copiar(Inicio);
            Final = size();
            Inicio = 0;
            cola = ColaAux;
        }
        cola[Final]=element;
        Final=(Final+1)%cola.length;
    }
    /**
     * Remueve el elemento en el frente de la cola.
     * @return Elemento removido.
     * @throws EmptyQueueException si la cola est� vac�a.
     */
    public E dequeue() throws EmptyQueueException{
        if(isEmpty()){
            throw new EmptyQueueException("Cola vacia");
        }
        E ret=cola[Inicio];
        cola[Inicio]=null;
        Inicio=(Inicio+1)%cola.length;
        return ret;
    }
}

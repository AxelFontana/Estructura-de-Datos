package TDALista;
import java.util.Iterator;
import Exceptions.*;


public class ListaDobleEnlaze<E>implements PositionList<E> {

    protected Nodo<E> Inicial,Final;
    protected int size;

    public ListaDobleEnlaze() {
        size=0;
        Inicial=new Nodo<E>(null,null,null);
        Final=new Nodo<E>(null,null,null);
        Inicial.setNext(Final);
        Final.setPrev(Inicial);
    }
    private Nodo<E>checkPosition(Position<E>pos)throws InvalidPositionException{
        if(pos==null||pos==Final||pos==Inicial) {
            throw new InvalidPositionException("Posicion Invalida");
        }
        Nodo<E>ret=null;
        try {
            ret=(Nodo<E>)pos;
        }
        catch(ClassCastException e) {
            throw new InvalidPositionException("Posicion Invaida");
        }
        return ret;
    }

    /**
     * Consulta la cantidad de elementos de la lista.
     * @return Cantidad de elementos de la lista.
     */
    public int size() {
        return size;
    }
    /**
     * Consulta si la lista est� vac�a.
     * @return Verdadero si la lista est� vac�a, falso en caso contrario.
     */
    public boolean isEmpty() {
        return size==0;
    }
    /**
     * Devuelve la posici�n del primer elemento de la lista.
     * @return Posici�n del primer elemento de la lista.
     * @throws EmptyListException si la lista est� vac�a.
     */
    public Position<E> first() throws EmptyListException{
        if(isEmpty()) {
            throw new EmptyListException("Lista Vacia");
        }
        return Inicial.getNext();
    }
    /**
     * Devuelve la posici�n del �ltimo elemento de la lista.
     * @return Posici�n del �ltimo elemento de la lista.
     * @throws EmptyListException si la lista est� vac�a.
     *
     */
    public Position<E> last() throws EmptyListException{
        if (isEmpty()) {
            throw new EmptyListException("Lista Vacia");
        }
        return Final.getPrev();
    }
    /**
     * Devuelve la posici�n del elemento siguiente a la posici�n pasada por par�metro.
     * @param p Posici�n a obtener su elemento siguiente.
     * @return Posici�n del elemento siguiente a la posici�n pasada por par�metro.
     * @throws InvalidPositionException si el posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
     * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al �ltimo elemento de la lista.
     */
    public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
        Nodo<E>nodo=checkPosition(p);
        if(isEmpty()) {
            throw new InvalidPositionException("Lista Vacia");
        }
        if(Final.getPrev()==nodo) {
            throw new BoundaryViolationException("Caes fuera de la lista");
        }
        return nodo.getNext();
    }
    /**
     * Devuelve la posici�n del elemento anterior a la posici�n pasada por par�metro.
     * @param p Posici�n a obtener su elemento anterior.
     * @return Posici�n del elemento anterior a la posici�n pasada por par�metro.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
     * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al primer elemento de la lista.
     */
    public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
        Nodo<E>nodo=checkPosition(p);
        if(isEmpty()) {
            throw new InvalidPositionException("Lista Vacia");
        }
        if(nodo==Inicial.getNext()) {
            throw new BoundaryViolationException("Caes fuera de la lista");
        }
        return nodo.getPrev();
    }
    /**
     * Inserta un elemento al principio de la lista.
     * @param element Elemento a insertar al principio de la lista.
     */
    public void addFirst(E element) {
        Nodo<E>nuevo=new Nodo<E>(element,Inicial.getNext(),Inicial);
        Inicial.getNext().setPrev(nuevo);
        Inicial.setNext(nuevo);
        size++;
    }

    /**
     * Inserta un elemento al final de la lista.
     * @param element Elemento a insertar al final de la lista.
     */
    public void addLast(E element) {
        Nodo<E>nuevo=new Nodo<E>(element,Final,Final.getPrev());
        Final.getPrev().setNext(nuevo);
        Final.setPrev(nuevo);
        size++;
    }
    /**
     * Inserta un elemento luego de la posici�n pasada por par�matro.
     * @param p Posici�n en cuya posici�n siguiente se insertar� el elemento pasado por par�metro.
     * @param element Elemento a insertar luego de la posici�n pasada como par�metro.
     * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
     */
    public void addAfter(Position<E> p, E element) throws InvalidPositionException{
        if (isEmpty()) {
            throw new InvalidPositionException("Lista Vacia");
        }
        Nodo<E>nodo=checkPosition(p);
        Nodo<E>nuevo=new Nodo<E>(element,nodo.getNext(),nodo);
        //enlazo bien la lista
        nodo.getNext().setPrev(nuevo);
        nodo.setNext(nuevo);
        size++;

    }
    /**
     * Inserta un elemento antes de la posici�n pasada como par�metro.
     * @param p Posici�n en cuya posici�n anterior se insertar� el elemento pasado por par�metro.
     * @param element Elemento a insertar antes de la posici�n pasada como par�metro.
     * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
     */
    public void addBefore(Position<E> p, E element) throws InvalidPositionException{
        if(isEmpty()) {
            throw new InvalidPositionException("PosicionInvalida");
        }
        Nodo<E>nodo=checkPosition(p);
        Nodo<E>nuevo=new Nodo<E>(element,nodo,nodo.getPrev());
        //Enlazo bien la lista
        nodo.getPrev().setNext(nuevo);
        nodo.setPrev(nuevo);
        size++;
    }
    /**
     * Remueve el elemento que se encuentra en la posici�n pasada por par�metro.
     * @param p Posici�n del elemento a eliminar.
     * @return element Elemento removido.
     * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
     */
    public E remove(Position<E> p) throws InvalidPositionException{
        if (isEmpty()) {
            throw new InvalidPositionException("Lista Vacia");
        }
        Nodo<E>nodo=checkPosition(p);
        E ret=nodo.element();
        nodo.getNext().setPrev(nodo.getPrev());
        nodo.getPrev().setNext(nodo.getNext());
        size--;
        return ret;
    }
    /**

     * Establece el elemento en la posici�n pasados por par�metro. Reemplaza el elemento que se encontraba anteriormente en esa posici�n y devuelve el elemento anterior.
     * @param p Posici�n a establecer el elemento pasado por par�metro.
     * @param element Elemento a establecer en la posici�n pasada por par�metro.
     * @return Elemento anterior.
     * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
     */
    public E set(Position<E> p, E element) throws InvalidPositionException{
        if (isEmpty()) {
            throw new InvalidPositionException("Lista Vacia");
        }
        Nodo<E>aux=checkPosition(p);
        E elemento=aux.element();
        aux.setElement(element);
        return elemento;
    }

    /**
     * Devuelve un un iterador de todos los elementos de la lista.
     * @return Un iterador de todos los elementos de la lista.
     */
    public Iterator<E> iterator(){return new ElementIterator<E>(this);}
    /**
     * Devuelve una colecci�n iterable de posiciones.
     * @return Una colecci�n iterable de posiciones.
     */
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> lista = new ListaDobleEnlaze<Position<E>>();
        Nodo<E> p = Inicial.getNext();
        while(p!=Final){
            lista.addLast(p);
            p = p.getNext();
        }
        return lista;
    }
}

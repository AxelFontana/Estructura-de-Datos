package TDALista;

public class Nodo<E>implements Position<E> {

    private Nodo<E> next,prev;
    private E element;

    public Nodo(E e, Nodo<E> n, Nodo<E> p) {
        element=e; next=n;	prev=p;
    }

    public E element(){
        return element;
    }
    public Nodo<E> getPrev(){
        return prev;
    }
    public Nodo<E> getNext(){
        return next;
    }
    public void setPrev(Nodo<E> p) {
        prev=p;
    }
    public void setNext(Nodo<E> n){
        next=n;
    }
    public void setElement(E e){
        element=e;
    }
}

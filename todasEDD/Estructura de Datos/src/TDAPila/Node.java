package TDAPila;

public class Node<E> {
    private E elemento;
    private Node<E>siguiente;

    public Node(E e,Node<E>n){
        elemento=e;
        siguiente=n;
    }
    public E getElement() {
        return elemento;
    }

    public Node<E> getSiguiente(){
        return siguiente;
    }

    public void setElement(E elem) {
        elemento = elem;
    }

    public void setSiguiente(Node<E> s) {
        siguiente = s;
    }
}

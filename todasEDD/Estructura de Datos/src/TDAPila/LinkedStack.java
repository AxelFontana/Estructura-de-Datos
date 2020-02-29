package TDAPila;

import Exceptions.EmptyStackException;

public class LinkedStack<E> implements Stack<E> {

    protected Node<E> top;
    protected int size;

    public LinkedStack(){
        top=null;
        size=0;
    }

    /**
     * Consulta la cantidad de elementos de la pila.
     * @return Cantidad de elementos de la pila.
     */
    public int size(){
        return size;

    }

    /**
     * Consulta si la pila está vacía.
     * @return Verdadero si la pila está vacía, falso en caso contrario.
     */
    public boolean isEmpty(){
        return size==0;
    }

    /**
     * Examina el elemento que se encuentra en el tope de la pila.
     * @return Elemento que se encuentra en el tope de la pila.
     * @throws EmptyStackException si la pila está vacía.
     */
    public E top()throws EmptyStackException{
        if(size==0){
            throw new EmptyStackException("Pila Vacia");
        }
        else{
            return top.getElement();
        }
    }

    /**
     * Inserta un elemento en el tope de la pila.
     * @param element Elemento a insertar.
     */
    public void push(E element){
        Node<E>nuevo=new Node<>(element,top);
        top=nuevo;
        size++;
    }

    /**
     * Remueve el elemento que se encuentra en el tope de la pila.
     * @return Elemento removido.
     * @throws EmptyStackException si la pila está vacía.
     */
    public E pop() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException("Pila Vacia");
        } else {
            E ret = top.getElement();
            top = top.getSiguiente();
            size--;
            return ret;

        }
    }
}

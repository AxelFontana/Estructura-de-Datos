package TDAArbol;

import Exceptions.*;
import TDALista.ListaDobleEnlaze;
import TDALista.Position;
import TDALista.PositionList;

import java.util.Iterator;

public class Arbol<E>implements Tree<E> {

    protected int size;
    protected TNodo<E>raiz;

    public Arbol(){
        size=0;
        raiz=null;
    }

    private TNodo<E>checkPosition(Position<E>v)throws InvalidPositionException{
        if(v==null){
            throw new InvalidPositionException(("Posicion Invalida"));
        }
        TNodo<E> n = null;
        try{
            n=(TNodo<E>)v;
        }
        catch (ClassCastException e){
            throw new InvalidPositionException("Posicion Invalida");
        }
        return n;
    }
    private void pre(PositionList<Position<E>> list,TNodo<E>pos){
        list.addLast(pos);
        for (TNodo<E> nodo:pos.getHijos())
            pre(list,nodo);
    }

    /**
     * Consulta la cantidad de nodos en el �rbol.
     *
     * @return Cantidad de nodos en el �rbol.
     */
    public int size(){
        return size;
    }

    /**
     * Consulta si el �rbol est� vac�o.
     *
     * @return Verdadero si el �rbol est� vac�o, falso en caso contrario.
     */
    public boolean isEmpty(){
        return size==0;
    }

    /**
     * Devuelve un iterador de los elementos almacenados en el �rbol en preorden.
     *
     * @return Iterador de los elementos almacenados en el �rbol.
     */
    public Iterator<E> iterator(){
        PositionList<E> l =new ListaDobleEnlaze<E>();
        for(Position<E> p:positions()){
            l.addLast(p.element());
        }
        return l.iterator();
    }

    /**
     * Devuelve una colecci�n iterable de las posiciones de los nodos del �rbol.
     *
     * @return Colecci�n iterable de las posiciones de los nodos del �rbol.
     */
    public Iterable<Position<E>> positions(){
        PositionList<Position<E>>lista = new ListaDobleEnlaze<Position<E>>();
        if(!isEmpty()){
            pre(lista,raiz);
        }
        return lista;
    }

    /**
     * Reemplaza el elemento almacenado en la posici�n dada por el elemento pasado por par�metro. Devuelve el elemento reemplazado.
     *
     * @param v Posici�n de un nodo.
     * @param e Elemento a reemplazar en la posici�n pasada por par�metro.
     * @return Elemento reemplazado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
     */
    public E replace(Position<E> v, E e) throws InvalidPositionException{
        if(isEmpty()){
            throw new InvalidPositionException("posicion Invalida");
        }
        TNodo<E> pos= checkPosition(v);
        E ret = pos.element();
        pos.setElemento(e);
        return ret;
    }

    /**
     * Devuelve la posici�n de la ra�z del �rbol.
     *
     * @return Posici�n de la ra�z del �rbol.
     * @throws EmptyTreeException si el �rbol est� vac�o.
     */
    public Position<E> root() throws EmptyTreeException{
        if(size==0){
            throw new EmptyTreeException("Raiz nula");
        }
        return raiz;
    }

    /**
     * Devuelve la posici�n del nodo padre del nodo correspondiente a una posici�n dada.
     *
     * @param v Posici�n de un nodo.
     * @return Posici�n del nodo padre del nodo correspondiente a la posici�n dada.
     * @throws InvalidPositionException   si la posici�n pasada por par�metro es inv�lida.
     * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde a la ra�z del �rbol.
     */
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException{
        if (isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        TNodo<E>nodo=checkPosition(v);
        if(nodo==raiz){
            throw new BoundaryViolationException("Te caes del arbol");
        }
        return nodo.getPadre();
    }

    /**
     * Devuelve una colecci�n iterable de los hijos del nodo correspondiente a una posici�n dada.
     *
     * @param v Posici�n de un nodo.
     * @return Colecci�n iterable de los hijos del nodo correspondiente a la posici�n pasada por par�metro.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
     */
    public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException{
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        TNodo<E> nodo=checkPosition(v);
        PositionList<Position<E>>listaHijos =new ListaDobleEnlaze<Position<E>>();

        for(TNodo<E> hijo : nodo.getHijos()){
            listaHijos.addLast(hijo);
        }
        return listaHijos;
    }

    /**
     * Consulta si una posici�n corresponde a un nodo interno.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo interno, falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
     */
    public boolean isInternal(Position<E> v) throws InvalidPositionException{
        if (isEmpty()){
            throw new InvalidPositionException("Arbol Vacio");
        }
        TNodo<E> nodo=checkPosition(v);
        return nodo.getHijos().size()>=1;
    }

    /**
     * Consulta si una posici�n dada corresponde a un nodo externo.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo externo, falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
     */
    public boolean isExternal(Position<E> v) throws InvalidPositionException{
        if (isEmpty()){
            throw new InvalidPositionException("Arbol Vacio");
        }
        TNodo<E> nodo=checkPosition(v);
        return nodo.getHijos().size()==0;
    }

    /**
     * Consulta si una posici�n dada corresponde a la ra�z del �rbol.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero, si la posici�n pasada por par�metro corresponde a la ra�z del �rbol,falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
     */
    public boolean isRoot(Position<E> v) throws InvalidPositionException{
        if(isEmpty()){
            throw new InvalidPositionException("Arbol Vacio");
        }
        TNodo<E>nodo=checkPosition(v);
        return nodo==raiz;
    }

    /**
     * Crea un nodo con r�tulo e como ra�z del �rbol.
     *
     * @param e R�tulo que se asignar� a la ra�z del �rbol.
     * @throws InvalidOperationException si el �rbol ya tiene un nodo ra�z.
     */
    public void createRoot(E e) throws InvalidOperationException{
        if(!isEmpty()){
            throw new InvalidOperationException("El arbol ya tiene raiz");
        }
        TNodo<E>nuevo=new TNodo<E>(e,null);
        raiz=nuevo;
        size++;
    }

    /**
     * Agrega un nodo con r�tulo e como primer hijo de un nodo dado.
     *
     * @param e R�tulo del nuevo nodo.
     * @param p Posici�n del nodo padre.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o el �rbol est� vac�o.
     */
    public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException{
        if (isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        TNodo<E> padre= checkPosition(p);
        TNodo<E> nuevo= new TNodo<E>(e,padre);
        padre.getHijos().addFirst(nuevo);
        size++;
        return nuevo;
    }

    /**
     * Agrega un nodo con r�tulo e como �timo hijo de un nodo dado.
     *
     * @param e R�tulo del nuevo nodo.
     * @param p Posici�n del nodo padre.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o el �rbol est� vac�o.
     */
    public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException{
        if (isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        TNodo<E> padre= checkPosition(p);
        TNodo<E> nuevo= new TNodo<E>(e,padre);
        padre.getHijos().addLast(nuevo);
        size++;
        return nuevo;
    }

    /**
     * Agrega un nodo con r�tulo e como hijo de un nodo padre dado. El nuevo nodo se agregar� delante de otro nodo tambi�n dado.
     *
     * @param e  R�tulo del nuevo nodo.
     * @param p  Posici�n del nodo padre.
     * @param rb Posici�n del nodo que ser� el hermano derecho del nuevo nodo.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida, o el �rbol est� vac�o, o la posici�n rb no corresponde a un nodo hijo del nodo referenciado por p.
     */
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException{
        if(size<=1){
            throw new InvalidPositionException("Arbol Vacio");
        }
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hijoDer = checkPosition(rb);
        TNodo<E> nuevo = new TNodo<E>(e,padre);


        if(padre.getHijos().isEmpty()){
            throw new InvalidPositionException("Posicion invalida");
        }

        try{
            boolean esta=false;// Ve si existe el hermano
            Position<TNodo<E>>posAct=padre.getHijos().first();
            while(!esta && posAct!=null){
                if(posAct.element()==hijoDer){
                    esta=true;
                }
                else{
                    if(posAct==padre.getHijos().last()){
                        posAct=null;
                    }
                    else{
                        posAct=padre.getHijos().next(posAct);
                    }
                }
            }
            if(!esta){
                throw new InvalidPositionException("No existe el nodo rb");
            }
            padre.getHijos().addBefore(posAct,nuevo);
            size++;
        } catch (EmptyListException | BoundaryViolationException ex) {
            ex.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Agrega un nodo con r�tulo e como hijo de un nodo padre dado. El nuevo nodo se agregar� a continuaci�n de otro nodo tambi�n dado.
     *
     * @param e  R�tulo del nuevo nodo.
     * @param p  Posici�n del nodo padre.
     * @param lb Posici�n del nodo que ser� el hermano izquierdo del nuevo nodo.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida, o el �rbol est� vac�o, o la posici�n lb no corresponde a un nodo hijo del nodo referenciado por p.
     */
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException{
        if(size<=1){
            throw new InvalidPositionException("Arbol Vacio");
        }
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hijoIz = checkPosition(lb);
        TNodo<E> nuevo = new TNodo<E>(e,padre);
        boolean esta=false;// Ve si existe el hermano

        if(padre.getHijos().isEmpty()){
            throw new InvalidPositionException("Posicion invalida");
        }

        try{
            Position<TNodo<E>>posAct=padre.getHijos().first();
            while(!esta && posAct!=null){
                if(posAct.element()==hijoIz){
                    esta=true;
                }
                else{
                    if(posAct==padre.getHijos().last()){
                        posAct=null;
                    }
                    else{
                        posAct=padre.getHijos().next(posAct);
                    }
                }
            }
            if(!esta){
                throw new InvalidPositionException("No existe el nodo rb");
            }
            padre.getHijos().addAfter(posAct,nuevo);
            size++;
        } catch (EmptyListException | BoundaryViolationException ex) {
            ex.printStackTrace();
        }
        return nuevo;
    }

    /**
     * Elimina el nodo referenciado por una posici�n dada, si se trata de un nodo externo.
     *
     * @param p Posici�n del nodo a eliminar.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o no corresponde a un nodo externo, o el �rbol est� vac�o.
     */
    public void removeExternalNode(Position<E> p) throws InvalidPositionException{
        if (isInternal(p)){
            throw new InvalidPositionException("p es un nodo interno");
        }
        try{
            TNodo<E>nodo= checkPosition(p);
            if(size==1){
                raiz=null;
            }
            else{

                TNodo<E> padre = nodo.getPadre();
                PositionList<TNodo<E>> hermanos=padre.getHijos();
                boolean es=false;
                Position<TNodo<E>> posAct= hermanos.first();

                while(!es && posAct!=null){
                    if(posAct.element()==nodo){
                        es=true;
                    }
                    else{
                        if (posAct==hermanos.last()){
                            posAct=null;
                        }
                        else{
                            posAct=hermanos.next(posAct);
                        }
                    }
                }
                if(!es){
                    throw new InvalidPositionException("No esta el nodo a eliminar");
                }
                else{
                    hermanos.remove(posAct);
                }
            }
        } catch (EmptyListException | BoundaryViolationException e) {
            e.printStackTrace();
        }
        size--;
    }

    /**
     * Elimina el nodo referenciado por una posici�n dada, si se trata de un nodo interno. Los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen.
     * Si el nodo a eliminar es la ra�z del �rbol,  �nicamente podr� ser eliminado si tiene un solo hijo, el cual lo reemplazar�.
     *
     * @param p Posici�n del nodo a eliminar.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o no corresponde a un nodo interno o corresponde a la ra�z (con m�s de un hijo), o el �rbol est� vac�o.
     */
    public void removeInternalNode(Position<E> p) throws InvalidPositionException{
        if (isExternal(p)){
            throw new InvalidPositionException("El nodo es externo");
        }
        TNodo<E>nodo = checkPosition(p);

        try{
            if(nodo==raiz){
                if(nodo.getHijos().size()>1){
                    throw new InvalidPositionException("Raiz tiene mas de un hijo");
                }
                if(nodo.getHijos().size()==1){
                    raiz = nodo.getHijos().remove(nodo.getHijos().first());
                    raiz.setPadre(null);
                }
            }
            else{
                TNodo<E> padre = nodo.getPadre();
                PositionList<TNodo<E>>hermanos = padre.getHijos();
                PositionList<TNodo<E>>hijosNodo = nodo.getHijos();
                Position<TNodo<E>> posActual = hermanos.first();//primera posicion de los hijos de padre
                boolean encontre=false;

                while(!encontre && posActual!=null){// busco a nodo en los hijos del padre
                    if(posActual.element() == nodo){
                        encontre = true;
                    }
                    else{
                        if(posActual == hermanos.last()){
                            posActual = null;
                        }
                        else{
                            posActual = hermanos.next(posActual);
                        }
                    }
                }
                 if(!encontre){
                     throw new InvalidPositionException("el nodo no se encuentra en los hijos de padre");
                 }
                 else{
                     for(TNodo<E> hijoNodo:hijosNodo){
                         hermanos.addBefore(posActual,hijoNodo);
                         hijoNodo.setPadre(padre);
                     }
                     hermanos.remove(posActual);
                 }
            }
        }
         catch (EmptyListException | BoundaryViolationException e) {
            e.printStackTrace();
        }
        size--;
    }

    /**
     * Elimina el nodo referenciado por una posici�n dada. Si se trata de un nodo interno. Los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen.
     * Si el nodo a eliminar es la ra�z del �rbol,  �nicamente podr� ser eliminado si tiene un solo hijo, el cual lo reemplazar�.
     *
     * @param p Posici�n del nodo a eliminar.
     * @throws Exceptions.InvalidPositionException si la posici�n pasada por par�metro es inv�lida o corresponde a la ra�z (con m�s de un hijo), o el �rbol est� vac�o.
     */
    public void removeNode(Position<E> p) throws InvalidPositionException{
        if(isInternal(p))
            removeInternalNode(p);
        else
        if(isExternal(p))
            removeExternalNode(p);
    }
}

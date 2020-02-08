package TDAMapeoABB;

import java.util.Comparator;

public class ABB <E extends Comparable<E>>{

    protected NodoABB<E> raiz;
    protected Comparator<E> comparador;
    /**
     * Inicializa un ABB vac�o.
     * Si no se parametriza un comparador de elementos, utiliza el comparador por defecto.
     * @param comp Comparador de elementos del ABB.
     */
    public ABB(Comparator<E> comp){
        raiz = new NodoABB<E>(null,null);
        if (comp != null){
            comparador = comp;
        }
        else{
            comparador = new DefaultComparator<E>();
        }
    }

    public NodoABB getRoot(){
        return raiz;
    }

    public boolean insertar (E elemento){
        boolean toReturn = false;

        //Se obtiene la ubicacion del nuevo elemento en el ABB
        NodoABB<E> nodo = buscar(elemento);
        //Si no existe en el ABB un nodo con el elemento igual al buscado

        if(nodo.getRotulo() == null){
            nodo.setRotulo(elemento);
            nodo.setLeft( new NodoABB<E>(null,nodo));
            nodo.setRight( new NodoABB<E>(null,nodo));
            toReturn = true;
        }
         return toReturn;
    }
    /**
     * Obtiene el nodo correspondiente donde se ubicar�a el elemento parametrizado.
     * @param elemento A buscar dentro del ABB.
     * @return Nodo cuyo elemento es el buscado; caso contrario, nodo DUMMY donde el elemento deber�a ser insertado.
     */
    public NodoABB<E> buscar(E elemento){
        return buscar_aux(raiz, elemento);
    }
    /**
     * M�todo auxiliar para computar la b�squeda del nodo en el ABB para un elemento dado.
     */
    private NodoABB<E> buscar_aux(NodoABB<E> nodo, E elemento){
        NodoABB<E> toRet;

        //Si la busqueda llega a un nodo dummy, lo retorna
        if(nodo.getRotulo() == null){
            toRet = nodo;
        }
        else{
            int c = comparador.compare(elemento, nodo.getRotulo());
            //Si los rotulos son iguales , se retorna el nodo visitado
            if( c == 0 ){
                toRet = nodo;
            }
            else{
                //Si el r�tulo buscado es menor al r�tulo del nodo visitado, se contin�a por el sub�rbol
                if( c < 0){
                    toRet = buscar_aux(nodo.getLeft(), elemento);
                }
                else{
                    toRet = buscar_aux(nodo.getRight(), elemento);
                }
            }

        }
        return toRet;
    }
    /**
     * Elimina el elemento del ABB, si este existe.
     * @param elemento Elemento a eliminar del ABB.
     * @return Elemento eliminado del ABB; null en caso contrario.
     */
    public E eliminar(E elemento){
        E toReturn = null;
        //Obtiene la ubicacion del nodo a eliminar
        NodoABB<E> nodo = buscar(elemento);
        if(nodo.getRotulo() == elemento){
            toReturn = nodo.getRotulo();
            eliminar_aux(nodo);
        }
        return toReturn;
    }
    private void eliminar_aux(NodoABB<E> nodo){
        //Si el elemento se encuentra en una hoja del ABB
        if(isExternal(nodo)){
            nodo.setRotulo(null);
            nodo.setRight(null);
            nodo.setLeft(null);
        }
        else{
            if(nodo == raiz){
                if(soloTieneHijoIzquierdo(nodo)){
                    nodo.getLeft().setParent(null);
                    raiz = nodo.getLeft();
                }
                else{
                    if(soloTieneHijoDerecho(nodo)){
                        nodo.getRight().setParent(null);
                        raiz = nodo.getRight();
                    }
                    else{
                        //se modifica el rotulo por el minimo valor del ABB en inorden a partir del nodo.
                        nodo.setRotulo(eliminarMinimo(nodo.getRight()));
                    }
                }
            }
            else{
                if(soloTieneHijoIzquierdo(nodo)){
                    if(nodo.getParent().getLeft() == nodo){
                        nodo.getParent().setLeft(nodo.getLeft());
                    }
                     else{
                         if(nodo.getParent().getRight() == nodo){
                             nodo.getParent().setRight(nodo.getRight());
                         }
                    }
                     nodo.getLeft().setParent(nodo.getParent());
                }
                else{
                    if(soloTieneHijoDerecho(nodo)){
                        if(nodo.getParent().getLeft() == nodo){
                            nodo.getParent().setLeft(nodo.getRight());
                        }
                        else{
                            nodo.getParent().setRight(nodo.getRight());
                        }
                        nodo.getRight().setParent(nodo.getParent());
                    }
                    else{
                        //Se modifica el rotulo por el minimo valor del ABB en inorden a partir de nodo
                        nodo.setRotulo(eliminarMinimo(nodo.getRight()));
                    }
                }
            }
        }
    }
    /**
     * Obtiene el nodo cuyo valor es m�nimo, a partir del sub�rbol izquierdo del nodo parametrizado.
     * @param nodo A partir del cual se busca el elemento m�nimo en su sub�rbol izquierdo.
     * @return Elemento m�nimo hallado.
     */
    private E eliminarMinimo( NodoABB<E> nodo ) {
        E toReturn;
        if( nodo.getLeft().getRotulo() == null ){
            toReturn = nodo.getRotulo();
            if( nodo.getRight().getRotulo() == null ) {
                nodo.setRotulo( null );
                nodo.setLeft( null );
                nodo.setRight( null );
            } else {
                nodo.getParent().setRight(nodo.getRight());
                nodo.getRight().setParent(nodo.getParent());
            }
        } else{
            toReturn = eliminarMinimo(nodo.getLeft());
        }
        return toReturn;
    }
    /**
     * Establece si un nodo es hoja en el ABB.
     * @param nodo A considerar si es hoja en el ABB.
     * @return Verdadero si el nodo es hoja, falso en caso contrario.
     */
    private boolean isExternal(NodoABB<E> nodo){
        return nodo.getLeft().getRotulo() == null && nodo.getRight().getRotulo() == null;
    }
    /**
     * Establece si un nodo solamente tiene hijo izquierdo.
     * @param nodo A considerar si tiene solamente hijo izquierdo.
     * @return Verdadero si nodo tiene solamente hijo izquierdo, falso en caso contrario.
     */
    private boolean soloTieneHijoIzquierdo(NodoABB<E> nodo ) {
        return nodo.getLeft().getRotulo() != null && nodo.getRight().getRotulo() == null;
    }
    /**
     * Establece si un nodo solamente tiene hijo derecho.
     * @param nodo A considerar si tiene solamente hijo derecho.
     * @return Verdadero si nodo tiene solamente hijo derecho, falso en caso contrario.
     */
    private boolean soloTieneHijoDerecho(NodoABB<E> nodo ) {
        return nodo.getLeft().getRotulo() == null && nodo.getRight().getRotulo() != null;
    }
    /**
     * Chequea si un elemento pertenece al ABB.
     * @param elemento Elemento a chequear su pertenencia.
     * @return Verdadero si el elemento pertenece al ABB, falso en caso contrario.
     */
    public boolean pertenece(E elemento){
        return buscar(elemento).getRotulo() != null;
    }
}

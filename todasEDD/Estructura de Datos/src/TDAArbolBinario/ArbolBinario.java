package TDAArbolBinario;

import java.util.Iterator;

import Exceptions.*;
import TDALista.*;

public class ArbolBinario<E> implements BinaryTree<E> {
    protected BTPosition<E> raiz;
    protected int size;

    public ArbolBinario() {
        raiz = null;
        size = 0;
    }

    private BTNode<E> checkPosition(Position<E> v) throws InvalidPositionException {
        if (v == null)
            throw new InvalidPositionException("Posicion Invalida");
        BTNode<E> n = null;
        try {
            n = (BTNode<E>) v;
        } catch (ClassCastException e) {
            throw new InvalidPositionException("Posicion invalida");
        }
        return n;
    }

    /**
     * Consulta la cantidad de nodos en el �rbol.
     *
     * @return Cantidad de nodos en el �rbol.
     */
    public int size() {
        return size;
    }

    /**
     * Consulta si el �rbol est� vac�o.
     *
     * @return Verdadero si el �rbol est� vac�o, falso en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator<E> iterator() {
        Iterable<Position<E>> positions = positions();
        PositionList<E> elements = new ListaDobleEnlaze<E>();
        for (Position<E> pos : positions) {
            elements.addLast(pos.element());
        }

        return elements.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> positions = new ListaDobleEnlaze<Position<E>>();
        if (size != 0) {
            try {
                preorderPositions(root(), positions);
            } catch (InvalidPositionException | EmptyTreeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return positions;
    }

    protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
        pos.addLast(v);
        try {
            if (hasLeft(v))
                preorderPositions(left(v), pos);
            if (hasRight(v))
                preorderPositions(right(v), pos);
        } catch (BoundaryViolationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Reemplaza el elemento almacenado en la posici�n dada por el elemento pasado
     * por par�metro. Devuelve el elemento reemplazado.
     *
     * @param v Posici�n de un nodo.
     * @param e Elemento a reemplazar en la posici�n pasada por par�metro.
     * @return Elemento reemplazado.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public E replace(Position<E> v, E e) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        E ret = nodo.element();
        nodo.setElement(e);
        return ret;
    }

    /**
     * Devuelve la posici�n de la ra�z del �rbol.
     *
     * @return Posici�n de la ra�z del �rbol.
     * @throws EmptyTreeException si el �rbol est� vac�o.
     */
    public Position<E> root() throws EmptyTreeException {
        if (size == 0) {
            throw new EmptyTreeException("Arbol vacio");
        }
        return raiz;
    }

    /**
     * Devuelve la posici�n del nodo padre del nodo correspondiente a una posici�n
     * dada.
     *
     * @param v Posici�n de un nodo.
     * @return Posici�n del nodo padre del nodo correspondiente a la posici�n dada.
     * @throws InvalidPositionException   si la posici�n pasada por par�metro es
     *                                    inv�lida.
     * @throws BoundaryViolationException si la posici�n pasada por par�metro
     *                                    corresponde a la ra�z del �rbol.
     */
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodo = checkPosition(v);
        if (nodo == raiz) {
            throw new BoundaryViolationException("Caes del arbol");
        }
        return nodo.getParent();
    }

    /**
     * Devuelve una colecci�n iterable de los hijos del nodo correspondiente a una
     * posici�n dada.
     *
     * @param v Posici�n de un nodo.
     * @return Colecci�n iterable de los hijos del nodo correspondiente a la
     * posici�n pasada por par�metro.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        PositionList<Position<E>> lista = new ListaDobleEnlaze<Position<E>>();
        if (nodo.getLeft() != null) {
            lista.addLast(nodo.getLeft());
        }
        if (nodo.getRight() != null) {
            lista.addLast(nodo.getRight());
        }
        return lista;
    }

    /**
     * Consulta si una posici�n corresponde a un nodo interno.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo
     * interno, falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        return (nodo.getLeft() != null || nodo.getRight() != null);
    }

    /**
     * Consulta si una posici�n dada corresponde a un nodo externo.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo
     * externo, falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public boolean isExternal(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        return (nodo.getLeft() == null && nodo.getRight() == null);
    }

    /**
     * Consulta si una posici�n dada corresponde a la ra�z del �rbol.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero, si la posici�n pasada por par�metro corresponde a la ra�z
     * del �rbol,falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        return raiz == nodo;
    }

    /**
     * Devuelve la posici�n del hijo izquierdo de v.
     *
     * @param v Posici�n de un nodo.
     * @return Posici�n del hijo izquierdo de v.
     * @throws InvalidPositionException   si la posici�n pasada por par�metro es
     *                                    inv�lida.
     * @throws BoundaryViolationException si v no tiene hijo izquierdo.
     */
    public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodo = checkPosition(v);
        if (nodo.getLeft() == null) {
            throw new BoundaryViolationException("No tiene hijo izquierdo");
        }
        return nodo.getLeft();
    }

    /**
     * Devuelve la posici�n del hijo derecho de v.
     *
     * @param v Posici�n de un nodo.
     * @return Posici�n del hijo derecho de v.
     * @throws InvalidPositionException   si la posici�n pasada por par�metro es
     *                                    inv�lida.
     * @throws BoundaryViolationException si v no tiene hijo derecho.
     */
    public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPosition<E> nodo = checkPosition(v);
        if (nodo.getRight() == null) {
            throw new BoundaryViolationException("No tiene hijo derecho");
        }
        return nodo.getRight();
    }

    /**
     * Testea si v tiene un hijo izquierdo.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si v tiene un hijo izquierdo y falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public boolean hasLeft(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        return nodo.getLeft() != null;
    }

    /**
     * Testea si v tiene un hijo derecho.
     *
     * @param v Posici�n de un nodo.
     * @return Verdadero si v tiene un hijo derecho y falso en caso contrario.
     * @throws InvalidPositionException si la posici�n pasada por par�metro es
     *                                  inv�lida.
     */
    public boolean hasRight(Position<E> v) throws InvalidPositionException {
        BTPosition<E> nodo = checkPosition(v);
        return nodo.getRight() != null;
    }

    /**
     * Crea un nodo con r�tulo e como ra�z del �rbol.
     *
     * @param r R�tulo que se asignar� a la ra�z del �rbol.
     * @throws InvalidOperationException si el �rbol ya tiene un nodo ra�z.
     */
    public Position<E> createRoot(E r) throws InvalidOperationException {
        if (size != 0) {
            throw new InvalidOperationException("El arbol ya tiene raiz");
        }
        raiz = new BTNode<E>(r, null, null, null);
        size++;
        return raiz;
    }

    /**
     * Agrega un nodo con r�tulo r como hijo izquierdo de un nodo dado.
     *
     * @param r R�tulo del nuevo nodo.
     * @param v Posici�n del nodo padre.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException  si la posici�n pasada por par�metro es
     *                                   inv�lida o el �rbol est� vac�o.
     * @throws InvalidOperationException si v ya tiene un hijo izquierdo.
     */
    public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
        if (size == 0) {
            throw new InvalidPositionException("El arbol esta vacio");
        }
        BTNode<E> nodo = checkPosition(v);
        if (nodo.getLeft() != null) {
            throw new InvalidOperationException("El nodo ya tiene un hijo izquierdo");
        }
        BTPosition<E> nodoN = new BTNode<E>(r, nodo, null, null);
        nodo.setLeft(nodoN);
        size++;
        return nodoN;
    }

    /**
     * Agrega un nodo con r�tulo r como hijo derecho de un nodo dado.
     *
     * @param r R�tulo del nuevo nodo.
     * @param v Posici�n del nodo padre.
     * @return La posici�n del nuevo nodo creado.
     * @throws InvalidPositionException  si la posici�n pasada por par�metro es
     *                                   inv�lida o el �rbol est� vac�o.
     * @throws InvalidOperationException si v ya tiene un hijo derecho.
     */
    public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
        if (size == 0) {
            throw new InvalidPositionException("El arbol esta vacio");
        }
        BTNode<E> nodo = checkPosition(v);
        if (nodo.getRight() != null) {
            throw new InvalidOperationException("El nodo ya tiene un hijo derecho");
        }
        BTPosition<E> nodoN = new BTNode<E>(r, nodo, null, null);
        nodo.setRight(nodoN);
        size++;
        return nodoN;
    }

    /**
     * Elimina el nodo referenciado por una posici�n dada. Si el nodo tiene un �nico
     * hijo, el nodo eliminado ser� reemplazado por su �nico hijo.
     *
     * @param v Posici�n del nodo a eliminar.
     * @return el r�tulo del nodo eliminado.
     * @throws InvalidPositionException  si la posici�n pasada por par�metro es
     *                                   inv�lida o el �rbol est� vac�o.
     * @throws InvalidOperationException si el nodo a eliminar tiene mas de un hijo.
     */
    public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
        if (size == 0)
            throw new InvalidPositionException("El arbol esta vacio");
        BTNode<E> nodo = checkPosition(v);
        BTPosition<E> hijoI = nodo.getLeft();
        BTPosition<E> hijoD = nodo.getRight();
        BTPosition<E> hijo = null;

        if (hijoI != null && hijoD != null)
            throw new InvalidOperationException("El nodo a eliminar tiene mas de un hijo");
        if (hijoI != null)
            hijo = hijoI;
        else {
            if (hijoD != null)
                hijo = hijoD;
            else
                hijo = null;
        }
        if (nodo == raiz) {
            if (hijo != null)
                hijo.setParent(null);
            raiz = hijo;
        } else {
            BTPosition<E> padre = nodo.getParent();
            if (nodo == padre.getLeft())
                padre.setLeft(hijo);
            else
                padre.setRight(hijo);
            if (hijo != null)
                hijo.setParent(padre);
        }

        size--;
        return v.element();
    }

    public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
        BTPosition<E> raiz_local = checkPosition(p);
        if (raiz_local.getLeft() != null || raiz_local.getRight() != null)
            throw new InvalidPositionException("La posicion no corresponde a un nodo hoja");
        try {
            // Clonaci�n de T1 como sub�rbol izquierdo
            if (!t1.isEmpty()) {
                Position<E> raiz_t1 = t1.root();
                BTPosition<E> hi_raiz_local = new BTNode<E>(raiz_t1.element(), raiz_local, null, null);
                raiz_local.setLeft(hi_raiz_local);
                clonar(raiz_local.getLeft(), raiz_t1, t1);
            }
            // Clonaci�n de T2 como sub�rbol derecho
            if (!t2.isEmpty()) {
                Position<E> raiz_t2 = t2.root();
                BTPosition<E> hd_raiz_local = new BTNode<E>(raiz_t2.element(), raiz_local, null, null);
                raiz_local.setRight(hd_raiz_local);
                clonar(raiz_local.getRight(), raiz_t2, t2);
            }
            size += t1.size() + t2.size();
        } catch (EmptyTreeException e) {
            raiz_local.setLeft(null);
            raiz_local.setRight(null);
        }
    }

    protected void clonar(BTPosition<E> padre_local, Position<E> padre_t, BinaryTree<E> t) {
        try {
            // Si existe hijo izquierdo en T de padre_t, se clona este y el sub�rbol a
            // partir del hijo izquierdo de padre_t.
            if (t.hasLeft(padre_t)) {
                Position<E> hi_padre_t = t.left(padre_t);
                BTPosition<E> hi_padre_local = new BTNode<E>(hi_padre_t.element(), padre_local, null, null);
                padre_local.setLeft(hi_padre_local);
                clonar(hi_padre_local, hi_padre_t, t);
            }
            // Si existe hijo derecho en T de padre_t, se clona este y el sub�rbol a partir
            // del hijo derecho de padre_t.
            if (t.hasRight(padre_t)) {
                Position<E> hd_padre_t = t.right(padre_t);
                BTPosition<E> hd_padre_local = new BTNode<E>(hd_padre_t.element(), padre_local, null, null);
                padre_local.setRight(hd_padre_local);
                clonar(hd_padre_local, hd_padre_t, t);
            }
        } catch (InvalidPositionException | BoundaryViolationException e) {
            padre_local.setLeft(null);
            padre_local.setRight(null);
        }
    }
}

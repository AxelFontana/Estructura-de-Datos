package TDAArbol;

import TDALista.ListaDobleEnlaze;
import TDALista.Position;
import TDALista.PositionList;

public class TNodo<E> implements Position<E> {

    protected E elem;
    protected TNodo<E> padre;
    protected PositionList<TNodo<E>> hijos;

    public TNodo(E elem,TNodo<E>p){
        this.elem=elem;
        padre=p;
        hijos=new ListaDobleEnlaze<TNodo<E>>();
    }

    public TNodo<E> getPadre(){
        return padre;
    }

    public E element() {
        return elem;
    }
    public PositionList<TNodo<E>> getHijos(){
        return hijos;
    }

    public void setPadre(TNodo<E>padre){
        this.padre=padre;
    }

    public void setElemento(E e){
        elem=e;
    }

}

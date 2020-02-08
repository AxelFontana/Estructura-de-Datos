package TDALista;
import java.util.NoSuchElementException;
import Exceptions.*;
import java.util.Iterator;
public class ElementIterator<E> implements Iterator<E> {

    private PositionList<E> l;
    private Position<E> p ;

    public ElementIterator(PositionList<E> lista) {
        try{
            l = lista;
            p = lista.isEmpty() ? null :  lista.first();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public boolean hasNext() {
        return p!=null;
    }

    @Override
    public E next() throws NoSuchElementException {
        E ret = null;
        try{
            if (p==null)
                throw new NoSuchElementException();
            ret = p.element();
            p = l.last() == p ? null : l.next(p);
        }catch(BoundaryViolationException | EmptyListException | InvalidPositionException e){}

        return ret;
    }

}
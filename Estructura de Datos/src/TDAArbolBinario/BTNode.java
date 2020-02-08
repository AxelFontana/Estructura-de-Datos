package TDAArbolBinario;

public class BTNode<E> implements BTPosition<E>{
    protected E elemento;
    protected BTPosition<E> parent,left,right;

    public BTNode(E e, BTPosition<E> p, BTPosition<E> l, BTPosition<E> r) {
        elemento = e;
        parent = p;
        left = l;
        right = r;
    }
    @Override
    public E element() {
        return elemento;
    }

    @Override
    public void setElement(E e) {
        elemento = e;
    }

    @Override
    public void setLeft(BTPosition<E> e) {
        left = e;
    }

    @Override
    public void setRight(BTPosition<E> e) {
        right = e;
    }

    @Override
    public void setParent(BTPosition<E> e) {
        parent = e;
    }

    @Override
    public BTPosition<E> getRight() {
        return right;
    }

    @Override
    public BTPosition<E> getLeft() {
        return left;
    }

    @Override
    public BTPosition<E> getParent() {
        return parent;
    }

}
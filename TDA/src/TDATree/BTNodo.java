package TDATree;

public class BTNodo<E> implements BTPosition<E> {
	private E elemento;
	private BTPosition<E> left, right, parent;
	
	public BTNodo(E e, BTPosition<E> p, BTPosition<E> l, BTPosition<E> r) {
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

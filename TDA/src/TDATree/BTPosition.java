package TDATree;
import TDALista.Position;
public interface BTPosition<E> extends Position<E> {
	public void setElement(E e);
	public void setLeft(BTPosition<E> e);
	public void setRight(BTPosition<E> e);
	public void setParent(BTPosition<E> e);
	public BTPosition<E> getRight();
	public BTPosition<E> getLeft();
	public BTPosition<E> getParent();
}

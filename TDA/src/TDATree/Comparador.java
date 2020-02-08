package TDATree;
import java.util.Comparator;
public class Comparador<E extends Comparable<E>> implements Comparator<E> {
	
	public int compare(E e1, E e2) {
		return e1.compareTo(e2);
	}
}

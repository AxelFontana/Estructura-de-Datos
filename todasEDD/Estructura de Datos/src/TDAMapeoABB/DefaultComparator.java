package TDAMapeoABB;

import java.util.Comparator;

public class DefaultComparator <E extends Comparable<E>> implements Comparator<E> {
    public int compare(E o1, E o2) {
        return o1.compareTo(o2);
    }
}

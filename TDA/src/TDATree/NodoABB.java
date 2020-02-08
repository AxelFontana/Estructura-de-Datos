package TDATree;

public class NodoABB<E extends Comparable<E>>{
	private E elem;
	private NodoABB<E> padre, hijoD, hijoI;
	
	
	public NodoABB(E e) {
		elem = e;
		padre = null;
		hijoD = null;
		hijoI = null;
	}
	
	public NodoABB() {
		this(null);
	}

	public NodoABB(E e, NodoABB<E> p) {
		elem = e;
		padre = p;
	}

	public E getElem() {
		return elem;
	}

	public void setElem(E elem) {
		this.elem = elem;
	}

	public NodoABB<E> getPadre() {
		return padre;
	}

	public void setPadre(NodoABB<E> padre) {
		this.padre = padre;
	}

	public NodoABB<E> getHijoD() {
		return hijoD;
	}

	public void setHijoD(NodoABB<E> hijoD) {
		this.hijoD = hijoD;
	}

	public NodoABB<E> getHijoI() {
		return hijoI;
	}

	public void setHijoI(NodoABB<E> hijoI) {
		this.hijoI = hijoI;
	}

}

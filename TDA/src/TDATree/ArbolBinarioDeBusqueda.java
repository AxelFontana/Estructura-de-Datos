package TDATree;
import java.util.Iterator;
import Exceptions.*;
import java.util.Comparator;

public class ArbolBinarioDeBusqueda<E extends Comparable<E>>{
	
	private int size;
	private NodoABB<E> raiz;
	private Comparator<E> comp;
	
	public ArbolBinarioDeBusqueda(Comparator<E> comp) {
		size = 0;
		raiz = new NodoABB<E>();
		this.comp = comp;
	}
	
	public boolean pertenece(E k) {
		return buscar(k).getElem() != null;
	}
	
	private NodoABB<E> buscar(E k){
		return buscarAux(k,raiz);
	}
	
	private NodoABB<E> buscarAux(E k, NodoABB<E> n){
		NodoABB<E> toRet = null;
		if(n.getElem() == null)
			toRet = n;
		else {
			int c = comp.compare(k, n.getElem());
			if(c == 0)
				toRet = n;
			else
				if(c<0)
					toRet = buscarAux(k, n.getHijoI());
				else
					toRet = buscarAux(k, n.getHijoD());
		}
		
		return toRet;
	}
	
	public void insertar(E k) {
		NodoABB<E> n = buscar(k);
		if(n.getElem() == null) {
			n.setElem(k);
			n.setHijoD(new NodoABB<E>(null,n));
			n.setHijoI(new NodoABB<E>(null,n));
			size++;
		}
	}
	
	public String toString() {
		return inOrden(raiz);
	}
	
	private String inOrden(NodoABB<E> n) {
		String toRet = "";
		if(n.getElem() != null) {
			toRet = "("+inOrden(n.getHijoI())+n.getElem()+inOrden(n.getHijoD())+")";
		}
		
		return toRet;
	}
	
	public E eliminar(E k) {
		E toRet = null;
		NodoABB<E> n = buscar(k);
		if(n.getElem()!=null) {
			toRet = n.getElem();
			eliminarAux(n);
			size--;
		}
		
		return toRet;
	}
	
	private boolean isExternal(NodoABB<E> n) {
		return n.getHijoD().getElem() == null && n.getHijoI().getElem() == null;
	}
	
	private boolean soloTieneHijoIzquierdo(NodoABB<E> n) {
		return n.getHijoI().getElem() != null && n.getHijoD().getElem() == null;
	}
	
	private boolean soloTieneHijoDerecho(NodoABB<E> n) {
		return n.getHijoD().getElem() != null && n.getHijoI().getElem() == null;
	}
	
	private void eliminarAux(NodoABB<E> n) {
		if(isExternal(n)) {
			n.setElem(null);
			n.setHijoD(null);
			n.setHijoI(null);
		}
		else {
			if(soloTieneHijoIzquierdo(n) && n!=raiz) {
				if(n.getPadre().getHijoI() == n)
					n.getPadre().setHijoI(n.getHijoI());
				else
					n.getPadre().setHijoD(n.getHijoI());
				n.getHijoI().setPadre(n.getPadre());
			}
			else {
				if(soloTieneHijoDerecho(n) && n!=raiz) {
					if(n.getPadre().getHijoI() == n)
						n.getPadre().setHijoI(n.getHijoD());
					else
						n.getPadre().setHijoD(n.getHijoD());
					n.getHijoD().setPadre(n.getPadre());	
				}
				else {
					n.setElem(eliminarMinimo(n.getHijoD()));
				}
			}
		}
	}
	
	private E eliminarMinimo(NodoABB<E> n) {
		E toRet = null;
		if(n.getHijoI().getElem() == null) {
			toRet = n.getElem();
			if(n.getHijoD().getElem() == null) {
				n.setElem(null);
				n.setHijoD(null);
				n.setHijoI(null);
			}
			else {
				n.getPadre().setHijoD(n.getHijoD());
				n.getHijoD().setPadre(n.getPadre());
			}
		}
		
		else {
			toRet = eliminarMinimo(n.getHijoI());
		}
		
		return toRet;
	}
}

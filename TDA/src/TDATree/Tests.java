package TDATree;

import java.util.Iterator;

import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import TDALista.DoubleLinkedList;
import TDALista.Position;
import TDALista.PositionList;

public class Tests {
	public static void main(String [] args) {
		Tree<Integer> arbol = new Arbol<Integer>();
		Tree<Integer> arbol2 = new Arbol<Integer>();
		try {
		arbol.createRoot(1);
		Position<Integer> r = arbol.root();
		Position<Integer> p1 = arbol.addLastChild(r, 2);
		Position<Integer> p2 = arbol.addLastChild(r, 3);
		arbol.addLastChild(r, 4);
		Position<Integer> p3 = arbol.addLastChild(p1, 5);
		Position<Integer> p4 = arbol.addLastChild(p1, 6);
		arbol.addLastChild(p2, 7);
		arbol.addLastChild(p3, 8);
		arbol.addLastChild(p4, 10);
		arbol.addLastChild(p4, 11);
		arbol.addLastChild(p4, 12);
		System.out.println(pre(arbol,r));
		arbol2.createRoot(6);
		Position<Integer> r2 = arbol2.root();
		arbol2.addLastChild(r2, 11);
		arbol2.addLastChild(r2, 12);
		System.out.println(pre(arbol2,r2));
		System.out.println(SubArbol(arbol2,arbol));
		}
		catch(EmptyTreeException | InvalidOperationException | InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void agregaHijo(Tree<Integer> T, int R, int A) {
		if(!T.isEmpty())
			try {
				if(altura(T,T.root()) == A) {
					T.addLastChild(T.root(), R);
				}
				else
					agregaHijoRec(T,R,A,T.root());
			}
			catch(EmptyTreeException|InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
	}
	
	private static <E> void agregaHijoRec(Tree<E> T, E R, int A, Position<E> raiz ) {
		try {
			for(Position<E> h: T.children(raiz)) {
				if(altura(T,h) == A)
					T.addLastChild(h, R);
				else
					agregaHijoRec(T,R,A,h);
			}
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static<E> int altura(Tree<E> T, Position<E> n) {
		int alt = -1;
		try {
			if(T.isExternal(n))
				alt = 0;
			else {
				for(Position<E> h: T.children(n)) {
					alt = Math.max(alt, altura(T,h));
				}
				alt++;
			}
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return alt;
	}
	
	private static <E> String escribeLista(PositionList<Position<E>> l) {
		String cad = "";
			for(Position<E> e: l) {
				cad+=" "+e.element();
			}
		return cad;
	}
	
	public static<E> String pre(Tree<E> T, Position<E> r) {
		String s = "";
		try {
			s+=r.element().toString();
			for(Position<E> h: T.children(r))
				s+="-"+pre(T,h);
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}
	
	public static <E> boolean SubArbol(Tree<E> A1, Tree<E> A2) {
		boolean encontre = false;
		try {
			Position<E> p;
			Iterator<Position<E>> it = A2.positions().iterator();
			
			while(it.hasNext() && !encontre) {
				p = it.next();
				if(p.element().equals(A1.root().element()))
					encontre = RecAux(p, A1.root(), A1, A2);
			}
		}
		catch(EmptyTreeException e) {
			e.printStackTrace();
		}
		return encontre;
	}
	
	private static <E> boolean RecAux(Position<E> p2, Position<E> p1, Tree<E> A1, Tree<E> A2) {
		boolean es = true;
		Iterator<Position<E>> it1 = null,it2;
		try {
			it1 = A1.children(p1).iterator();
			it2 = A2.children(p2).iterator();
			Position<E> Pit1, Pit2;
			while(it1.hasNext() && it2.hasNext() && es) {
				Pit1 = it1.next();
				Pit2 = it2.next();
				if(!(Pit1.element().equals(Pit2.element())))
					es = false;
				else
					RecAux(Pit2,Pit1,A1,A2);
			}
		}
		catch(InvalidPositionException e) {
			e.printStackTrace();
		}
		return es && !it1.hasNext();
	}
}  

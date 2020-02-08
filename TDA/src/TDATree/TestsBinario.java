package TDATree;
import TDALista.Position;
import Exceptions.*;
public class TestsBinario {
	public static <E> int cantNodos(BinaryTree<E> T) {
		int cant = 0;
		try {	
			if(!T.isEmpty()) {
				cant = 1;
				cant+=cantNodosRec(T, T.root());
			}
		}
		catch (EmptyTreeException e) {
				e.printStackTrace();
		}
		return cant;
	}
	
	private static <E> int cantNodosRec(BinaryTree<E> T, Position<E> r) {
		int cant = 0;
		try {
			if(T.hasLeft(r)) {
				cant++;
				cantNodosRec(T,T.left(r));
			}
			if(T.hasRight(r)) {
				cant++;
				cantNodosRec(T, T.right(r));
			}
		}
		catch(InvalidPositionException|BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}
		return cant;
	}
	
	public static <E> BinaryTree<E> espejo(BinaryTree<E> T){
		BinaryTree<E> arbolEspejo = new ArbolBinarioEnlazado<E>();
		try {
			if(!T.isEmpty()) {
				arbolEspejo.createRoot(T.root().element());
				espejoRec(T,arbolEspejo, T.root(), arbolEspejo.root());
			}
		}
		catch(InvalidOperationException|EmptyTreeException e) {
			System.out.println(e.getMessage());
		}
		return arbolEspejo;
	}
	
	private static <E> void espejoRec(BinaryTree<E> T, BinaryTree<E> TE, Position<E> r, Position<E> re) {
		try {
			if(T.hasLeft(r)) {
				Position<E> p1 = TE.addRight(re, T.left(r).element());
				espejoRec(T,TE,T.left(r),p1);
			}
			
			if(T.hasRight(r)) {
				Position<E> p2 = TE.addLeft(re, T.right(r).element());
				espejoRec(T,TE,T.right(r), p2);
			}
		}
		catch(InvalidPositionException|BoundaryViolationException|InvalidOperationException e) {
			System.out.println(e.getMessage());			
		}
	}
	
	public static <E> String preOrden(BinaryTree<E> T) {
		String cad = "";
		try {
			cad = pre(T,T.root());
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	private static <E> String pre(BinaryTree<E> T, Position<E> raiz) {
		String cad = "";
		try {
			cad+=raiz.element().toString()+"-";
			if(T.hasLeft(raiz))
				cad+=pre(T,T.left(raiz));
				
			if(T.hasRight(raiz))
				cad+=pre(T, T.right(raiz));
				
		}
		catch(InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	public static void main(String [] args) {
		BinaryTree<Integer> arbol = new ArbolBinarioEnlazado<Integer>();
		try {
			Position<Integer> r = arbol.createRoot(1);
			Position<Integer> h1 = arbol.addLeft(r, 2);
			Position<Integer> h2 = arbol.addRight(r, 3);
			arbol.addLeft(h1, 4);
			arbol.addRight(h1, 5);
			arbol.addLeft(h2, 6);
			System.out.println(preOrden(arbol));
			BinaryTree<Integer> nuevo = espejo(arbol);
			System.out.println(preOrden(nuevo));
		}
		catch(InvalidPositionException|InvalidOperationException e) {
			System.out.println(e.getMessage());
		}
	}
}

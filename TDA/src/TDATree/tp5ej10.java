package TDATree;
import TDACola.*;
import Exceptions.*;
import TDALista.*;
public class tp5ej10 {
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
	
	public static <E> String posOrden(BinaryTree<E> T) {
		String cad = "";
		try {
			cad = pos(T, T.root());
		}
		catch(EmptyTreeException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	private static <E> String pos(BinaryTree<E> T, Position<E> raiz) {
		String cad = "";
		try {
			if(T.hasLeft(raiz))
				cad+=pos(T, T.left(raiz));
			if(T.hasRight(raiz))
				cad+=pos(T,T.right(raiz));
			cad+="-"+raiz.element().toString();
		}
		catch(InvalidPositionException|BoundaryViolationException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	public static <E> String inOrden(BinaryTree<E> T) {
		String cad = "";
		try {
			cad = in(T, T.root());
		}
		catch(EmptyTreeException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	private static <E> String in(BinaryTree<E> T, Position<E> raiz) {
		String cad = "";
		try {
			if(T.hasLeft(raiz))
				cad+=in(T, T.left(raiz));
			cad+="-"+raiz.element().toString();
			if(T.hasRight(raiz))
				cad+=in(T, T.right(raiz));
		}
		catch(InvalidPositionException|BoundaryViolationException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	public static <E> String porNiveles(BinaryTree<E> T) {
		String cad = "";
		try {
			cad = niv(T, T.root());
		}
		catch(EmptyTreeException e) {
			e.printStackTrace();
		}
		return cad;
	}
	
	private static <E> String niv(BinaryTree<E> T, Position<E> raiz) {
		String cad = "";
			try {
				Queue<Position<E>> cola = new ArrayQueue<Position<E>>();
				cola.enqueue(raiz);
				cola.enqueue(null);
				while(!cola.isEmpty()) {
					Position<E> n = cola.dequeue();
					if(n != null) {
						cad+=" "+n.element().toString()+" ";
						if(T.hasLeft(n))
							cola.enqueue(T.left(n));
						if(T.hasRight(n))
							cola.enqueue(T.right(n));
					}
					else {
						cad+="\n";
						if(!cola.isEmpty())
							cola.enqueue(null);
					}
				}
			}
			catch(BoundaryViolationException|InvalidPositionException | EmptyQueueException e) {
				e.printStackTrace();
			}
		return cad;
	}
	
	public static void main(String [] args) {
		BinaryTree<Integer> arbol = new ArbolBinarioEnlazado<Integer>();
		try {
			arbol.createRoot(1);
			Position<Integer> raiz = arbol.root();
			Position<Integer> p1 = arbol.addLeft(raiz, 2);
			Position<Integer> p2 = arbol.addRight(raiz, 3);
			arbol.addLeft(p1, 4);
			arbol.addRight(p1, 5);
			arbol.addLeft(p2, 6);
			System.out.println(porNiveles(arbol));
		}
		catch(EmptyTreeException | InvalidOperationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
} 

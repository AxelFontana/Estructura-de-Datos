package TDATree;
import TDALista.*;
import Exceptions.*;
public class tp5ej5 {
	public static <E> String post(Tree<E> T, Position<E> r) {
		String s = "";
		try {
		for(Position<E> h: T.children(r))
			s+=post(T,h)+"-";
		s+=r.element().toString();
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return s;
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
		 
	
	public static void main(String [] args) {
		Tree<Integer> T = new Arbol<Integer>();
		try {
			T.createRoot(1);
			Position<Integer> raiz = T.root();
			Position<Integer> p1 = T.addLastChild(raiz, 2);
			Position<Integer> p2 = T.addLastChild(raiz, 3);
			Position<Integer> p3 = T.addLastChild(raiz, 4);
			
			T.addLastChild(p1, 5);
			T.addLastChild(p1, 6);
			String cad1 = pre(T, raiz);
			String cad = post(T, raiz);
			System.out.println(cad);
			System.out.println(cad1);
		} catch (InvalidOperationException | InvalidPositionException | EmptyTreeException e) {
			e.printStackTrace();
		}
	}
		
}


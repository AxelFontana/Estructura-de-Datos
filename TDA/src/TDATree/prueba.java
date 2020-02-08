package TDATree;

import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import TDALista.Position;

public class prueba {
	public static void main(String []args) {
		Arbol<Integer> arbol = new Arbol<Integer>();
		try {
			arbol.createRoot(1);
			Position<Integer> r = arbol.root();
			arbol.addLastChild(r, 2);
			Position<Integer> h2 = arbol.addLastChild(r, 3);
			arbol.addLastChild(r, 4);
			arbol.addLastChild(r, 5);
			arbol.addLastChild(h2, 6);
			System.out.println(arbol.Altura(r));
			
		} catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

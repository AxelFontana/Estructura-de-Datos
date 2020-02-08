import TDALista.*;
import Exceptions.*;
import ImplementacionesListas.*;
import java.util.Iterator;
public class ej3dTp4 {
	public static PositionList<Integer> intercalar(PositionList<Integer> L1, PositionList<Integer> L2){
		PositionList<Integer> nueva = new ListaSimplementeEnlazada<Integer>();
		boolean corte;
		int aux;
		if(!L1.isEmpty() || !L2.isEmpty()) {
			Iterator<Integer> it2 = L2.iterator();
			aux = it2.next();
			for(Integer i: L1) {
				corte = false;
				if(it2.hasNext()) {
					while(!corte) {
						if(aux>i) {
							nueva.addLast(i);
							corte = true;
						}
						else {
							if(aux == i) {
								nueva.addLast(i);
								aux = it2.next();
								corte = true;
							}
							else {
								nueva.addLast(aux);
								aux = it2.next();
							}
						}
					}
				}
				else {
					nueva.addLast(i);
				}
			}
			nueva.addLast(aux);
			while(it2.hasNext()) {
				nueva.addLast(it2.next());
			}
		}
		return nueva;
	}
	
	public static String escribir(PositionList<Integer> l) {
		String cad = "";
			if(!l.isEmpty()) {
				try {
					Position<Integer> p = l.first();
					while(p!=null) {
						cad+=p.element()+" ";
						if(p != l.last())
							p = l.next(p);
						else
							p = null;
								
					}
				}
				catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
					System.out.println(e.getMessage());
				}
			}
		return cad;
	}
	public static void main(String [] args) {
		PositionList<Integer> L1 = new ListaSimplementeEnlazada<Integer>();
		PositionList<Integer> L2 = new ListaSimplementeEnlazada<Integer>();
		L1.addLast(1);
		L1.addLast(5);
		L1.addLast(7);
		L1.addLast(8);
		L2.addLast(2);
		L2.addLast(5);
		L2.addLast(6);
		L2.addLast(9);
		L2.addLast(10);
		L2.addLast(25);
		L2.addLast(45);
		L2.addLast(120);
		PositionList<Integer> L3 = intercalar(L1,L2);
		System.out.println(escribir(L1));
		System.out.println(escribir(L2));
		System.out.println(escribir(L3));
	}
}

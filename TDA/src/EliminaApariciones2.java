import ImplementacionesListas.*;
import Exceptions.*;
import TDALista.*;
public class EliminaApariciones2 {
	public static void elimina(Integer x, PositionList<Integer> l) {
		Position<Integer> p, a;
		if(!l.isEmpty()) {
			try {
				p = l.first();
				while(p!=l.last()) {
					if(p.element().equals(x)) {
						a = p;
						p = l.next(p);
						l.remove(a);
					}
					a = p;
					p = l.next(p);
				}
			}
			catch(BoundaryViolationException|InvalidPositionException|EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
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
		PositionList<Integer> l = new ListaSimplementeEnlazada<Integer>();
		l.addFirst(5);
		l.addLast(3);
		l.addLast(8);
		l.addLast(2);
		l.addLast(3);
		System.out.println(escribir(l));
		elimina(3, l);
		System.out.println(escribir(l));
	}
}

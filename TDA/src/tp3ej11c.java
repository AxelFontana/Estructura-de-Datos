import TDALista.*;
import ImplementacionesListas.*;
import Exceptions.*;
public class tp3ej11c {
	public static <E> void AvanzaYBorra(PositionList<E> lc, int n) {
		try {
			Position<E> p = null;
			Position<E> aux;
			int cant = 0;
			if(!lc.isEmpty())
				p = lc.first();
			while(lc.size()>1) {
				p = lc.next(p);
				cant++;
				if(cant == n) {
					aux = p;
					lc.remove(p);
					cant = 0;
					p = lc.next(aux);
				}
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static <E> String escribir(PositionList<E> l) {
		String cad = "";
			if(!l.isEmpty()) {
				try {
					Position<E> p = l.first();
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
		PositionList<Integer> lc = new ListaCircular<Integer>();
		lc.addLast(1);
		lc.addLast(2);
		lc.addLast(4);
		lc.addLast(1);
		lc.addLast(1);
		lc.addLast(1);
		lc.addLast(5);
		lc.addLast(10);
		lc.addLast(19);
		lc.addLast(15);
		lc.addLast(1);
		lc.addLast(1);
		lc.addLast(4);
		lc.addLast(3);
		lc.addLast(2);
		lc.addLast(2);
		lc.addLast(1);
		lc.addLast(0);
		lc.addLast(45);
		lc.addLast(5);
		lc.addLast(1);
		escribir(lc);
		AvanzaYBorra(lc, 3);
		escribir(lc);
	}
}

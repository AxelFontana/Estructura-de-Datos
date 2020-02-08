import Exceptions.*;
import TDALista.*;
public class Metodos {
	public static PositionList<Integer> intercalar(PositionList<Integer> l1, PositionList<Integer> l2){
		PositionList <Integer> l3 = new ListaSimplementeEnlazada<Integer>();
		if(!l1.isEmpty() && !l2.isEmpty()) {
			try {
				Position<Integer> p1 = l1.first();
				Position<Integer> p2 = l2.first();
				while(p1!=null && p2!=null) {
					if(p1.element()<p2.element()) {
						l3.addLast(p1.element());
						if(p1!=l1.last())
							p1 = l1.next(p1);
						else
							p1 = null;
					}
					else {
						if(p1.element() == p2.element()) {
							l3.addLast(p1.element());
							if(p1 != l1.last())
								p1 = l1.next(p1);
							else
								p1 = null;
							if(p2 != l2.last())
								p2 = l2.next(p2);
							else
								p2 = null;
						}
						else {
							l3.addLast(p2.element());
							if(p2 != l2.last())
								p2 = l2.next(p2);
							else
								p2 = null;
						}
					}
				}
				
				while(p1 != null) {
					l3.addLast(p1.element());
					if(p1 != l1.last())
						p1 = l1.next(p1);
					else
						p1 = null;
				}
				
				while(p2 != null) {
					l3.addLast(p2.element());
					if(p2 != l2.last())
						p2 = l2.next(p2);
					else
						p2 = null;
				}
			}
			catch(BoundaryViolationException|EmptyListException|InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
		}
		return l3;
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
		L1.addLast(4);
		L1.addLast(5);
		L2.addLast(2);
		L2.addLast(3);
		L2.addLast(5);
		L2.addLast(9);
		PositionList<Integer> L3 = intercalar(L1,L2);
		System.out.println(escribir(L1));
		System.out.println(escribir(L2));
		System.out.println(escribir(L3));
	}
}